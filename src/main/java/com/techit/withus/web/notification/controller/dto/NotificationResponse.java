package com.techit.withus.web.notification.controller.dto;

import org.springframework.util.Assert;

import com.techit.withus.web.notification.domain.Notification;
import com.techit.withus.web.notification.domain.NotificationType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationResponse {
    Long memberId;
    String notificationContent;
    String relatedUrl;
    boolean isRead;
    NotificationType notificationType;

    @Builder
    public NotificationResponse(Long memberId, String notificationContent, String relatedUrl, boolean isRead,
        NotificationType notificationType) {
        this.memberId = memberId;
        this.notificationContent = notificationContent;
        this.relatedUrl = relatedUrl;
        this.isRead = isRead;
        this.notificationType = notificationType;
    }

    public static NotificationResponse from(Notification notification){
        if(notification.getReceiver() == null){
            Assert.notNull(notification.getReceiver(), "memberId must not be null");
        }
        return NotificationResponse.builder()
            .notificationContent(notification.getNotificationContent())
            .notificationType(notification.getNotificationType())
            .relatedUrl(notification.getRelatedUrl())
            .isRead(notification.getIsRead())
            .memberId(notification.getReceiver().getUserId())
            .build();
    }
}
