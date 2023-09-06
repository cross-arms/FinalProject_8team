package com.techit.withus.web.notification.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.techit.withus.common.fixture.chat.ChatMessageFixture;
import com.techit.withus.common.fixture.users.UserFixture;
import com.techit.withus.web.chat.controller.dto.MessageRequest;
import com.techit.withus.web.chat.service.MessageService;
import com.techit.withus.web.notification.controller.dto.NotificationEvent;
import com.techit.withus.web.notification.domain.NotificationType;
import com.techit.withus.web.notification.service.event.NotificationListener;
import com.techit.withus.web.users.domain.entity.Users;
import com.techit.withus.web.users.repository.UserRepository;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class NotificationServiceTest {
    @Autowired
    NotificationService notificationService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageService messageService;
    @Autowired
    ApplicationEventPublisher eventPublisher;
    @Autowired
    NotificationListener listener;

    @Test
    @DisplayName("알림 구독을 진행한다.")
    void subscribeTest() throws Exception {
        //given
        Users member = Users.builder().email("email1").password("password1").build();
        userRepository.save(member);
        String lastEventId = "";
        //when
        Assertions.assertDoesNotThrow(() -> notificationService.subscribe(member.getUserId(), lastEventId));
        SseEmitter emitter = notificationService.subscribe(member.getUserId(), lastEventId);
        org.assertj.core.api.Assertions.assertThat(emitter.getTimeout()).isEqualTo(360000L);
    }

    @Test
    @DisplayName("알림 메시지를 전송한다.")
    void sendTest() throws Exception {
        //given
        Users member = Users.builder().email("email1").password("password1").build();
        String lastEventId = "";
        notificationService.subscribe(member.getUserId(), lastEventId);
        //when
        Assertions.assertDoesNotThrow(
            () -> notificationService
                .send(
                    member,
                    NotificationType.MESSAGE,
                    member.getEmail(),
                    "https://withus:8080/rooms/1")
        );
    }

    @Test
    @DisplayName("채팅 메시지에 대한 알림을 보낸다.")
    void sendMessageNotification() throws Exception {
        //given
        NotificationEvent event = NotificationEvent.builder()
            .receivers(UserFixture.createUsersList(3))
            .notificationType(NotificationType.MESSAGE)
            .senderEmail("test@test.com")
            .build();
        //when
        Assertions.assertDoesNotThrow(() -> eventPublisher.publishEvent(event));
        //then
    }

    @Test
    @DisplayName("publish 된 이벤트를 처리 할 수 있다.")
    void notificationListenerTest() throws Exception {
        //given
        NotificationEvent event = NotificationEvent.builder()
            .receivers(UserFixture.createUsersList(3))
            .notificationType(NotificationType.MESSAGE)
            .senderEmail("test@test.com")
            .build();
        //when
        Assertions.assertDoesNotThrow(() -> listener.handleNotification(event));
    }

    @Test
    @DisplayName("메시지를 저장하고 알림을 보낸다.")
    void saveMessageAndSendNotification() throws Exception {
        //given
        MessageRequest messageRequest = ChatMessageFixture.createMessageRequest(1L);
        String userEmail = "user1@example.com";
        //when
        Assertions.assertDoesNotThrow(() -> messageService.save(messageRequest, userEmail));
    }
}