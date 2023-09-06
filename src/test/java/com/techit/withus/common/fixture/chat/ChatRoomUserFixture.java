package com.techit.withus.common.fixture.chat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.test.util.ReflectionTestUtils;

import com.techit.withus.common.fixture.users.UserFixture;
import com.techit.withus.web.chat.domain.ChatRoomUser;

public class ChatRoomUserFixture {
    public static final Long TEST_ID_A = 1L;
    public static final Long TEST_RANDOM_ID = 2099L;

    public static ChatRoomUser createDefaultChatRoomUser(){
        return ChatRoomUser.builder()
            .chatRoom(ChatRoomFixture.createDefaultChatRoom())
            .build();
    }
    public static ChatRoomUser createChatRoomUserWithUsername(String username){
        return ChatRoomUser.builder()
            .chatRoom(ChatRoomFixture.createDefaultChatRoom())
            .user(UserFixture.createUsersWithEmail(username))
            .build();
    }
    public static List<ChatRoomUser> createChatRoomUsersWithId(int size){
        List<ChatRoomUser> chatRoomUsers = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            chatRoomUsers.add(createChatRoomUserWithId((long)i));
        }
        return chatRoomUsers;
    }

    public static List<ChatRoomUser> createChatRoomUsersWithMultipleUsers(String username, int size){
        List<ChatRoomUser> chatRoomUsers = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            chatRoomUsers.add(createChatRoomUserWithUsernameAndId("normal user" + i, (long)i));
        }
        chatRoomUsers.add(createChatRoomUserWithUsernameAndId(username,TEST_RANDOM_ID));
        return chatRoomUsers;
    }

    public static ChatRoomUser createChatRoomUserWithId(Long id){
        ChatRoomUser chatRoomUser = createDefaultChatRoomUser();
        ReflectionTestUtils.setField(chatRoomUser, "id", id);
        return chatRoomUser;
    }

    public static ChatRoomUser createChatRoomUserWithUsernameAndId(String username, Long id){
        ChatRoomUser chatRoomUser = createChatRoomUserWithUsername(username);
        ReflectionTestUtils.setField(chatRoomUser, "id", id);
        return chatRoomUser;
    }
}
