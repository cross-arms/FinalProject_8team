package com.techit.withus.web.notification.controller.dto;

import java.util.List;

import com.techit.withus.web.notification.domain.NotificationType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationEvent {
    private List<Long> receiverIds;
    private NotificationType notificationType;
    private String senderEmail;
    private String relatedUri;

    @Builder
    public NotificationEvent(List<Long> receiverIds, NotificationType notificationType, String senderEmail, String relatedUri) {
        this.receiverIds = receiverIds;
        this.notificationType = notificationType;
        this.senderEmail = senderEmail;
        this.relatedUri = relatedUri;
    }
}
