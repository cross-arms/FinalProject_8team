package com.techit.withus.web.chat.domain;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.Assert;

import com.techit.withus.common.BaseTimeEntity;
import com.techit.withus.web.notification.controller.dto.NotificationEvent;
import com.techit.withus.web.notification.domain.NotificationType;
import com.techit.withus.web.users.domain.entity.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "messages")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_user_id", nullable = false)
    private ChatRoomUser chatRoomUser;
    private String message;

    @Builder
    public ChatMessage(ChatRoomUser chatRoomUser, String message) {
        validateMessage(message);
        this.chatRoomUser = chatRoomUser;
        this.message = message;
    }

    private void validateMessage(String message){
        if(message == null){
            Assert.notNull(message, "message must not be null");
        }
        if(message.isEmpty()){
            Assert.hasLength(message, "message must have at least a text");
        }
    }

    public void associateChatRoomUser(ChatRoomUser chatRoomUser){
        this.chatRoomUser = chatRoomUser;
        chatRoomUser.getChatMessages().add(this);
    }

    public void publishMessageEvent(ApplicationEventPublisher publisher, List<Users> receivers){
        publisher.publishEvent(
            NotificationEvent.builder()
                .receivers(receivers)
                .notificationType(NotificationType.MESSAGE)
                .senderEmail(chatRoomUser.getUser().getEmail())
                .relatedUri(String.valueOf(chatRoomUser.getChatRoom().getId()))
                .build()
        );
    }
}

