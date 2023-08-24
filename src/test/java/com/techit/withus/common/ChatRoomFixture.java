package com.techit.withus.common;

import com.techit.withus.web.chat.domain.ChatRoom;

public class ChatRoomFixture {
    private static final Long TEST_ID_A = 1L;
    public static final String INVALID_LENGTH_ROOM_NAME = createRoomNameWithMoreThan20Texts();
    private static final String TEST_ROOM_NAME_A = "Room A001";

    public static ChatRoom createDefaultChatRoomUser(){
        return ChatRoom.builder()
            .roomName(TEST_ROOM_NAME_A)
            .build();
    }

    private static String createRoomNameWithMoreThan20Texts(){
        StringBuilder sb = new StringBuilder();
        sb.append("a".repeat(21));
        return sb.toString();
    }
}