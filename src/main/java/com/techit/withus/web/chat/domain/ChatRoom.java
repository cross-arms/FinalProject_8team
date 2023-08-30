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
        validateChatRoom(roomName);
        this.roomName = roomName;
        if(chatRoomUsers != null)
            this.chatRoomUsers = chatRoomUsers;
    }

    private void validateChatRoom(String roomName){
        validateRoomName(roomName);
    }

    private static void validateRoomName(String roomName){
        if(roomName.length() > 20){
            throw new InvalidValueException(ErrorCode.INVALID_ROOM_NAME, roomName);
        }
    }


    public void updateChatRoom(String roomName, List<Users> users){
        this.roomName = roomName;
        addUsers(users);
    }

    public static ChatRoom createChatRoom(String roomName, List<ChatRoomUser> chatRoomUsers){
        validateRoomName(roomName);
        return ChatRoom.builder()
            .roomName(roomName)
            .chatRoomUsers(chatRoomUsers)
            .build();
    }

    public void addUsers(List<Users> users){
        List<ChatRoomUser> chatRoomUserList = users.stream().map(ChatRoomUser::createChatRoomUser).toList();
        chatRoomUserList.forEach(chatRoomUser -> chatRoomUser.associateChatRoom(this));
        chatRoomUsers.addAll(chatRoomUserList);
    }

    public void deleteUsers(List<Users> users){
        List<ChatRoomUser> usersToDelete = chatRoomUsers.stream().filter(chatRoomUser -> users.contains(chatRoomUser.getUser())).toList();
        chatRoomUsers.removeAll(usersToDelete);
    }
}

