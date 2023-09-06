package com.techit.withus.web.notification.domain.strategy;

public class FollowNotificationStrategy implements NotificationStrategy{

    private static final String FOLLOW_NOTIFICATION_FORMAT = "%s 님이 팔로우 당신을 팔로우 했습니다.";
    private static final String FOLLOW_RELATED_URL = "http://localhost:8080/%s";

    @Override
    public String createMessage(String senderEmail) {
        return String.format(FOLLOW_NOTIFICATION_FORMAT, senderEmail);
    }

    @Override
    public String createRelatedUrl(String relatedUri) {
        return String.format(FOLLOW_RELATED_URL, relatedUri);
    }
}
