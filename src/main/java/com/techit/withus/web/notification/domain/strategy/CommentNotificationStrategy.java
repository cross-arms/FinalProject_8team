package com.techit.withus.web.notification.domain.strategy;

public class CommentNotificationStrategy implements NotificationStrategy{
    private static final String COMMENT_NOTIFICATION_FORMAT = "%s 님이 당신에게 댓글을 남겼습니다.";
    private static final String COMMENT_RELATED_URL = "http://localhost:8080/%s";

    @Override
    public String createMessage(String senderEmail) {
        return String.format(COMMENT_NOTIFICATION_FORMAT, senderEmail);
    }

    @Override
    public String createRelatedUrl(String relatedUri) {
        return String.format(COMMENT_RELATED_URL, relatedUri);
    }
}
