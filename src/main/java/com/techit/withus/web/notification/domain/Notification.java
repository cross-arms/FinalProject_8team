package com.techit.withus.web.notification.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.Assert;

import com.techit.withus.web.users.domain.entity.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Users receiver;
    @Column(nullable = false)
    private String senderEmail;
    @Column(nullable = false)
    private String notificationContent;
    @Column(nullable = false)
    private String relatedUrl;
    //사용자가 알람을 읽었는지
    @Column(nullable = false)
    private Boolean isRead = false;
    @Enumerated(EnumType.STRING)
    NotificationType notificationType;


    @Builder(access = AccessLevel.PRIVATE)
    private Notification(String notificationContent, String relatedUrl, Boolean isRead,
        NotificationType notificationType, String senderEmail, Users receiver)
    {
        this.senderEmail = senderEmail;
        this.notificationContent = notificationContent;
        this.relatedUrl = relatedUrl;
        this.isRead = isRead;
        this.notificationType = notificationType;
        this.receiver = receiver;
    }

    public static Notification createNotification(final Users receiver, final String senderEmail, final NotificationType type, final String relatedUrl){
        return Notification.builder()
            .notificationType(type)
            .notificationContent(type.strategy().createMessage(senderEmail))
            .isRead(false)
            .receiver(receiver)
            .relatedUrl(relatedUrl)
            .build();
    }

    public void markMessageAsRead(Boolean isRead){
        this.isRead = isRead;
    }
}