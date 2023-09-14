package com.techit.withus.web.notification.service.event;

import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.techit.withus.common.exception.EntityNotFoundException;
import com.techit.withus.common.exception.ErrorCode;
import com.techit.withus.web.notification.controller.dto.NotificationEvent;
import com.techit.withus.web.notification.service.NotificationService;
import com.techit.withus.web.users.domain.entity.Users;
import com.techit.withus.web.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationListener {
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Async
    public void handleNotification(NotificationEvent event) {
        List<Users> receivers = userRepository.findByUserIdIn(event.getReceiverIds());
        if(receivers.isEmpty()) throw new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXIST);
        receivers.forEach(
            receiver -> notificationService.send(
                receiver,
                event.getNotificationType(),
                event.getSenderEmail(),
                event.getRelatedUri()
            )
        );
    }
}