package com.techit.withus.web.notification.controller.dto;

import java.util.List;

import com.techit.withus.web.notification.domain.NotificationType;
import com.techit.withus.web.users.domain.entity.Users;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationEvent {
    private List<Users> receivers;
    private NotificationType notificationType;
    private String senderEmail;
    private String relatedUri;

    @Builder
    public NotificationEvent(List<Users> receivers, NotificationType notificationType, String senderEmail, String relatedUri) {
        this.receivers = receivers;
        this.notificationType = notificationType;
        this.senderEmail = senderEmail;
        this.relatedUri = relatedUri;
    }
}
