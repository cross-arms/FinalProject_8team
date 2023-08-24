package com.techit.withus.common;

import org.springframework.test.util.ReflectionTestUtils;

import com.techit.withus.web.chat.domain.ChatMessage;

public class ChatMessageFixture {
    private static final String TEST_MESSAGE_A = "test message A";
    private static final Long TEST_ID_A = 1L;
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
}
