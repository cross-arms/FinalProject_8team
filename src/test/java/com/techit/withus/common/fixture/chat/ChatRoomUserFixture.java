package com.techit.withus.common.fixture.chat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.test.util.ReflectionTestUtils;

import com.techit.withus.web.chat.domain.ChatRoomUser;

public class ChatRoomUserFixture {
    public static final Long TEST_ID_A = 1L;

    public static ChatRoomUser createDefaultChatRoomUser(){
        return ChatRoomUser.builder()
            .chatRoom(ChatRoomFixture.createDefaultChatRoom())
            .build();
    }

    public static List<ChatRoomUser> createChatRoomUsersWithId(int size){
        List<ChatRoomUser> chatRoomUsers = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            chatRoomUsers.add(createChatRoomUserWithId((long)i));
        }
        return chatRoomUsers;
    }

    public static ChatRoomUser createChatRoomUserWithId(Long id){
        ChatRoomUser chatRoomUser = createDefaultChatRoomUser();
        ReflectionTestUtils.setField(chatRoomUser, "id", id);
        return chatRoomUser;
    }
}
