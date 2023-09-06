package com.techit.withus.web.notification.domain.strategy;

public class MessageNotificationStrategy implements NotificationStrategy {
    private static final String MESSAGE_NOTIFICATION_FORMAT = "%s 님이 당신에게 메시지를 보냈습니다.";
    private static final String MESSAGE_RELATED_URL = "http://localhost:8080/rooms/%s";
    @Override
    public String createMessage(String senderEmail) {
        return String.format(MESSAGE_NOTIFICATION_FORMAT, senderEmail);
    }

    @Override
    public String createRelatedUrl(String relatedUri) {
       return String.format(MESSAGE_RELATED_URL, relatedUri);
    }
}
