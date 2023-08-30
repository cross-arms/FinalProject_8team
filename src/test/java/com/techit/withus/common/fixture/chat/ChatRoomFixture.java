package com.techit.withus.common.fixture.chat;

import java.util.List;

import org.springframework.test.util.ReflectionTestUtils;

import com.techit.withus.web.chat.controller.dto.ChatRoomRequest;
import com.techit.withus.web.chat.controller.dto.RoomUpdateRequest;
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
    public static ChatRoom createTestAChatRoomWithId(Long id){
        ChatRoom chatRoom = ChatRoom.builder()
            .roomName("방 이름 1")
            .build();
        ReflectionTestUtils.setField(chatRoom, "id", id);
        return chatRoom;
    }

    private static String createRoomNameWithMoreThan20Texts(){
        StringBuilder sb = new StringBuilder();
        sb.append("a".repeat(21));
        return sb.toString();
    }

    public static ChatRoomRequest createChatRoomRequest(){
        return ChatRoomRequest.builder()
            .roomName("채팅방 1")
            .roomMemberIds(List.of(1L, 2L, 3L, 4L))
            .build();
    }

    public static RoomUpdateRequest createRoomUpdateRequest(){
        return RoomUpdateRequest.builder()
            .roomName("채팅방 2")
            .memberIds(List.of(1L, 2L, 3L, 4L))
            .build();
    }

}