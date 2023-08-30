package com.techit.withus.common.fixture.chat;

import org.springframework.test.util.ReflectionTestUtils;

import com.techit.withus.web.chat.controller.dto.MessageRequest;
import com.techit.withus.web.chat.domain.ChatMessage;

public class ChatMessageFixture {
    private static final String TEST_MESSAGE_A = "test message A";
    private static final Long TEST_ID_A = 1L;
    public static final Integer TEST_PAGE_NUMBER = 0;
    public static final Integer TEST_PAGING_SIZE = 3;
    public static final String TEST_MESSAGE_SEND_TIME =  "13:23";
    public static ChatMessage createDefaultChatMessage(){
        return ChatMessage.builder()
            .message(TEST_MESSAGE_A)
            .chatRoomUser(ChatRoomUserFixture.createChatRoomUserWithId(TEST_ID_A))
            .build();
    }

    public static ChatMessage createChatMessageWithId(Long id){
        ChatMessage chatMessage = createDefaultChatMessage();
        ReflectionTestUtils.setField(chatMessage, "id", id);
        return chatMessage;
    }

    public static MessageRequest createMessageRequest(Long roomId){
        return MessageRequest.builder()
            .roomId(roomId)
            .message(TEST_MESSAGE_A)
            .time(TEST_MESSAGE_SEND_TIME)
            .build();
    }
}
