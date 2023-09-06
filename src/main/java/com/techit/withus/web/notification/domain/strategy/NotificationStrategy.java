package com.techit.withus.web.notification.domain.strategy;

public interface NotificationStrategy {
    String createMessage(String senderEmail);
    String createRelatedUrl(String relatedUri);
}
