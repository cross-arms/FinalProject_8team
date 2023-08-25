package com.techit.withus.common;

import org.springframework.test.util.ReflectionTestUtils;

import com.techit.withus.web.chat.domain.ChatRoomUser;

public class ChatRoomUserFixture {
    private static final Long TEST_ID_A = 1L;

    public static ChatRoomUser createDefaultChatRoomUser(){
        return ChatRoomUser.builder()
            .chatRoom(ChatRoomFixture.createDefaultChatRoom())
            .build();
    }

    public static ChatRoomUser createChatRoomUserWithId(Long id){
        ChatRoomUser chatRoomUser = createDefaultChatRoomUser();
        ReflectionTestUtils.setField(chatRoomUser, "id", id);
        return chatRoomUser;
    }
}
