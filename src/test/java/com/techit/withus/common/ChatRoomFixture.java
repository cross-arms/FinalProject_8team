package com.techit.withus.common;

import com.techit.withus.web.chat.domain.ChatRoom;

public class ChatRoomFixture {
    public static final Long TEST_CHATROOM_ID_A = 1L;
    public static final String INVALID_LENGTH_ROOM_NAME = createRoomNameWithMoreThan20Texts();
    private static final String TEST_ROOM_NAME_A = "Room A001";

    public static ChatRoom createDefaultChatRoom(){
        return ChatRoom.builder()
            .roomName(TEST_ROOM_NAME_A)
            .build();
    }

    public static ChatRoom createTestAChatRoom(){
        return ChatRoom.builder()
            .roomName("방 이름 1")
            .build();
    }

    private static String createRoomNameWithMoreThan20Texts(){
        StringBuilder sb = new StringBuilder();
        sb.append("a".repeat(21));
        return sb.toString();
    }
}