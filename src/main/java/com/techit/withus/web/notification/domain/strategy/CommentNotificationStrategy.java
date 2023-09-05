package com.techit.withus.web.notification.domain.strategy;

public class CommentNotificationStrategy implements NotificationStrategy{
    private static final String COMMENT_MESSAGE_FORMAT = "%s 님이 당신에게 댓글을 남겼습니다.";
    @Override
    public String createMessage(String senderEmail) {
        return String.format(COMMENT_MESSAGE_FORMAT, senderEmail);
    }
}
