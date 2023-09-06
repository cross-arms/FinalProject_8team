package com.techit.withus.web.notification.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.techit.withus.web.notification.controller.dto.NotificationResponse;
import com.techit.withus.web.notification.domain.Notification;
import com.techit.withus.web.notification.domain.NotificationType;
import com.techit.withus.web.notification.repository.EmitterRepository;
import com.techit.withus.web.notification.repository.NotificationRepository;
import com.techit.withus.web.users.domain.entity.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
    public static final Long DEFAULT_TIME_OUT = 360000L;

    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;

    private String makeTimeIncludeId(Long memberId){
        return memberId + "_" + System.currentTimeMillis();
    }

    public SseEmitter subscribe(Long memberId, String lastEventId){
        String emitterId = makeTimeIncludeId(memberId);
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIME_OUT);
        sseEmitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        sseEmitter.onTimeout(() -> emitterRepository.deleteById(emitterId));
        emitterRepository.save(emitterId, sseEmitter);
        //503에러 방지를 위해 DUMMY 전송
        String eventId = makeTimeIncludeId(memberId);
        sendNotification(sseEmitter, eventId, emitterId, "EventStream Created. [userId : " + memberId + "]");
        if(hasLostData(lastEventId)){
            sendLostData(lastEventId, memberId, sseEmitter, emitterId);
        }
        return sseEmitter;
    }

    private String extractMemberId(String emitterId){
        int cut = emitterId.indexOf("_");
        return emitterId.substring(0, cut);
    }

    /**
     * 등록을 진행하고, SseEmitter의 유효시간 도안 어느 데이터도 전송되지 않으면,
     * 503에러를 발생시킴
     * -> 맨처음 연결을 진행하는 경우 DummyData를 보내서 이를 방지해야한다.
     * @param emitter
     * @param eventId lastEventId로 마지막 전송받은 이벤트 ID가 무엇인지 알고 받지 못한 데이터 정보들에 대해
     *                인지할수 있어야 한다.
     *                하지만 해당 회원 ID로 요청하는 수많은 이벤트가 존재
     *                -> 만약 그중에 가장 마지막에 전송받은 데이터를 구분하려면 어떻게 구분할까
     * Last-Event-Id = 1548
     * {1548, Notification1}
     * {1548, Notification2}
     * {1548, Notification3}
     *                이런식으로 id만 정의되어있으면 구분할 수 없다.
     * 구분자로 시간을 넣어주는 방식
     *  이를 위해 구분자로 시간을 넣어주면 어떨까?
     *
     * Last-Event-Id = 1548_15198456
     * {1548_15198456, Notification1}
     * {1548_16198456, Notification2}
     * @param emitterId
     * @param data
     */
    private void sendNotification(
        SseEmitter emitter,
        String eventId,
        String emitterId,
        Object data
    ){
        try{
            emitter.send(
                SseEmitter
                    .event()
                    .id(eventId)
                    .data(data));
        }catch (IOException ex){
            emitterRepository.deleteById(emitterId);
        }
    }

    private boolean hasLostData(String lastEventId){
        return !lastEventId.isEmpty();
    }

    private void sendLostData(String lastEventId, Long memberId, SseEmitter emitter, String emitterId){
        Map<String, Object> allEvents = emitterRepository
            .findAllEventCacheStartWithMemberId(String.valueOf(memberId));
        allEvents.entrySet().stream()
            .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
            .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
    }

    /**
     * 1. 어떤 회원에게 알림을 보낼지에 대해 찾는다.
     * 2. 알림을 받을 회원의 Emitter들을 모두 찾는다.
     * 3. 알림을 구성하고, 캐시에 저장한다.
     * 4. 알림에 대한 이벤트를 발생시킨다.
     * @param receiver : 수신자 엔티티
     * @param notificationType : 알림 타입 - FOLLOW, COMMENT
     * @param senderEmail : 발송자 이메일
     * @param url : 확인 url
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void send(Users receiver, NotificationType notificationType, String senderEmail, String url){
        Notification notification = notificationRepository.save(Notification.createNotification(receiver, senderEmail, notificationType, url));
        Long receiverId = receiver.getUserId();
        String eventId = makeTimeIncludeId(receiverId);
        Map<String, SseEmitter> receiverEmitters = emitterRepository.findAllEmitterStartWithMemberId(
            String.valueOf(receiverId));
        receiverEmitters.forEach((emitterId, emitter) -> {
            emitterRepository.saveEventCache(emitterId, notification);
            sendNotification(emitter, eventId, emitterId, NotificationResponse.from(notification));
        });
    }
}
