package com.techit.withus.web.notification.service.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.techit.withus.web.notification.controller.dto.NotificationEvent;
import com.techit.withus.web.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationListener {
    private final NotificationService notificationService;

    @TransactionalEventListener
    @Async
    public void handleNotification(NotificationEvent event){
        event.getReceivers().forEach(
            receiver -> notificationService.send(
                receiver,
                event.getNotificationType(),
                event.getSenderEmail(),
                event.getRelatedUri()
            )
        );
    }
}