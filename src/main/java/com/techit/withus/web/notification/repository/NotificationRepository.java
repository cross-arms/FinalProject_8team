package com.techit.withus.web.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techit.withus.web.notification.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
