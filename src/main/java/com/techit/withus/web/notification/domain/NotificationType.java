package com.techit.withus.web.notification.domain;

import com.techit.withus.web.notification.domain.strategy.CommentNotificationStrategy;
import com.techit.withus.web.notification.domain.strategy.FollowNotificationStrategy;
import com.techit.withus.web.notification.domain.strategy.MessageNotificationStrategy;
import com.techit.withus.web.notification.domain.strategy.NotificationStrategy;

public enum NotificationType {
    COMMENT(new CommentNotificationStrategy()), FOLLOW(new FollowNotificationStrategy()), MESSAGE(new MessageNotificationStrategy());

    private final NotificationStrategy notificationStrategy;

    NotificationType(NotificationStrategy notificationStrategy) {
        this.notificationStrategy = notificationStrategy;
    }

    public NotificationStrategy strategy(){
        return notificationStrategy;
    }
}
