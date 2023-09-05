package com.techit.withus.web.notification.domain.strategy;

public class FollowNotificationStrategy implements NotificationStrategy{

    private static final String FOLLOW_MESSAGE_FORMAT = "%s 님이 팔로우 당신을 팔로우 했습니다.";

    @Override
    public String createMessage(String senderEmail) {
        return String.format(FOLLOW_MESSAGE_FORMAT, senderEmail);
    }
}
