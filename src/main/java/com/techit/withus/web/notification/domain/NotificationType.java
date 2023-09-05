package com.techit.withus.web.notification.domain;

import org.aspectj.weaver.ast.Not;

import com.techit.withus.web.notification.domain.strategy.CommentNotificationStrategy;
import com.techit.withus.web.notification.domain.strategy.FollowNotificationStrategy;
import com.techit.withus.web.notification.domain.strategy.NotificationStrategy;

public enum NotificationType {
    COMMENT(new CommentNotificationStrategy()), FOLLOW(new FollowNotificationStrategy());

    private final NotificationStrategy notificationStrategy;

    NotificationType(NotificationStrategy notificationStrategy) {
        this.notificationStrategy = notificationStrategy;
    }

    public NotificationStrategy strategy(){
        return notificationStrategy;
    }
}
