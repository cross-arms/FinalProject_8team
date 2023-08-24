package com.techit.withus.web.chat.domain;

import java.util.ArrayList;
import java.util.List;

import com.techit.withus.common.BaseTimeEntity;
import com.techit.withus.common.exception.ErrorCode;
import com.techit.withus.common.exception.InvalidValueException;
import com.techit.withus.web.users.domain.entity.Users;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;
    @Column(nullable = false)
    private String roomName;
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ChatRoomUser> chatRoomUsers = new ArrayList<>();

    @Builder
    private ChatRoom(final String roomName, final List<ChatRoomUser> chatRoomUsers) {
        validateRoomName(roomName);
        this.roomName = roomName;
        this.chatRoomUsers = chatRoomUsers;
    }

    private static void validateRoomName(String roomName){
        if(roomName.length() > 20){
            throw new InvalidValueException(ErrorCode.INVALID_ROOM_NAME, roomName);
        }
    }

    public static ChatRoom createChatRoom(String roomName, List<ChatRoomUser> chatRoomUsers){
        validateRoomName(roomName);
        return ChatRoom.builder()
            .roomName(roomName)
            .chatRoomUsers(chatRoomUsers)
            .build();
    }

    public void addUsers(List<Users> users){
        users.stream().map(ChatRoomUser::createChatRoomUser)
            .forEach(chatRoomUser -> this.chatRoomUsers.add(chatRoomUser));
    }
}

