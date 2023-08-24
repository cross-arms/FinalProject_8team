package com.techit.withus.web.chat.domain;

import java.util.ArrayList;
import java.util.List;

import com.techit.withus.common.BaseTimeEntity;
import com.techit.withus.web.users.domain.entity.Users;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(uniqueConstraints = {@UniqueConstraint(name = "CHATROOM_USER_UNIQUE", columnNames = {"chat_room_id", "user_id"})})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomUser extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_user_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
    @OneToMany(mappedBy = "chatRoomUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @Builder
    private ChatRoomUser(ChatRoom chatRoom, Users user, List<ChatMessage> chatMessages) {
        this.chatRoom = chatRoom;
        this.user = user;
        this.chatMessages = chatMessages;
    }

    public static ChatRoomUser createChatRoomUser(Users user){
        return ChatRoomUser.builder()
            .user(user)
            .build();
    }
}
