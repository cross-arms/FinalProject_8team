package com.techit.withus.web.notification.repository;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.techit.withus.common.fixture.users.UserFixture;
import com.techit.withus.web.notification.domain.Notification;
import com.techit.withus.web.notification.domain.NotificationType;

public class EmitterRepositoryImplTest {
    private final EmitterRepository emitterRepository = new EmitterRepositoryImpl();
    private static final Long DEFAULT_TIMEOUT = 60L * 1000L * 60L;

    @Test
    @DisplayName("요청으로인한 새로운 Emitter추가")
    void save() throws Exception {
        //given
        Long memberId = 1L;
        String emitterId = memberId + "_" + System.currentTimeMillis();
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
        //when//then
        Assertions.assertDoesNotThrow(() -> emitterRepository.save(emitterId, sseEmitter));
    }

    @Test
    @DisplayName("수신한 이벤트를 캐시에 저장한다.")
    void saveEventCache() throws Exception {
        //given
        Long memberId = 1L;
        String eventCacheId = memberId + "_" + System.currentTimeMillis();
        Notification notification = Notification.createNotification(
            UserFixture.createUsers(),
            "test@test.com",
            NotificationType.FOLLOW,
            "https://withus.com"
        );        //when//then
        Assertions.assertDoesNotThrow(() -> emitterRepository.saveEventCache(eventCacheId, notification));
    }

    @Test
    @DisplayName("어떤 회원이 접속한 모드 Emitter를 찾는다.")
    void findAllEnitterStartWithByMemberId() throws Exception {
        //given
        Long memberId = 1L;
        String emitterId1 = memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId1, new SseEmitter(DEFAULT_TIMEOUT));
        Thread.sleep(1000);
        String emitterId2 = memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId2, new SseEmitter(DEFAULT_TIMEOUT));
        Thread.sleep(1000);
        String emitterId3 = memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId3, new SseEmitter(DEFAULT_TIMEOUT));
        //when
        Map<String, SseEmitter> result = emitterRepository.findAllEmitterStartWithMemberId(
            String.valueOf(memberId));
        //then
        Assertions.assertEquals(3, result.size());
    }

    @Test
    @DisplayName("어떤 회원에게 수신된 이벤트를 캐시에서 모두 찾는다.")
    void findAllEventCacheStartWithByMemberId() throws Exception {
        //given
        Long memberId = 1L;
        String eventId = memberId + "_" + System.currentTimeMillis();
        Notification notification = Notification.createNotification(
            UserFixture.createUsers(),
            "test@test.com",
            NotificationType.FOLLOW,
            "https://withus.com"
        );
        emitterRepository.saveEventCache(eventId, notification);
        String eventId2 = memberId + "_" + System.currentTimeMillis();
        Notification notification2 = Notification.createNotification(
            UserFixture.createUsers(),
            "test@test.com",
            NotificationType.COMMENT,
            "https://withus.com"
        );
        emitterRepository.saveEventCache(eventId2, notification2);
        //when
        Map<String, Object> result = emitterRepository.findAllEventCacheStartWithMemberId(
            String.valueOf(memberId));
        //then
        Assertions.assertEquals(2, result.size());
    }

    @Test
    @DisplayName("저장된 모든 Emitter를 제거한다.")
    void deleteAllEmittersWithId() throws Exception {
        //given
        Long memberId = 1L;
        String emitterId1 = memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId1, new SseEmitter(DEFAULT_TIMEOUT));
        Thread.sleep(1000);
        String emitterId2 = memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId2, new SseEmitter(DEFAULT_TIMEOUT));
        Thread.sleep(1000);
        String emitterId3 = memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId3, new SseEmitter(DEFAULT_TIMEOUT));
        //when
        emitterRepository.deleteById(String.valueOf(emitterId1));
        //then
        Assertions.assertEquals(2, emitterRepository.findAllEmitterStartWithMemberId(String.valueOf(memberId)).size());
    }

    @Test
    @DisplayName("저장된 모든 Emitter를 제거한다.")
    void deleteAllEmitterStartWithId() throws Exception {
        //given
        Long memberId = 1L;
        String emitterId1 = memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId1, new SseEmitter(DEFAULT_TIMEOUT));
        Thread.sleep(1000);
        String emitterId2 = memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId2, new SseEmitter(DEFAULT_TIMEOUT));
        Thread.sleep(1000);
        String emitterId3 = memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId3, new SseEmitter(DEFAULT_TIMEOUT));
        //when
        emitterRepository.deleteAllEmitterStartWithId(String.valueOf(memberId));
        //then
        Assertions.assertEquals(0, emitterRepository.findAllEmitterStartWithMemberId(String.valueOf(memberId)).size());
    }

    @Test
    @DisplayName("수신한 이벤트를 캐시에서 제거한다.")
    void deleteAllEventCacheStartWithId() throws Exception {
        //given
        Long memberId = 4L;
        //when
        emitterRepository.deleteAllEventCacheStartWithId(String.valueOf(memberId));
        //then
        Assertions.assertEquals(0, emitterRepository.findAllEventCacheStartWithMemberId(String.valueOf(memberId)).size());
    }
}
