package com.techit.withus.web.chat.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.techit.withus.common.fixture.chat.ChatRoomFixture;
import com.techit.withus.common.fixture.chat.ChatRoomUserFixture;
import com.techit.withus.common.exception.InvalidValueException;

class ChatTest {
    @Nested
    @DisplayName("ChatMessage 엔티티 테스트")
    class ChatMessageTest{

        @Test
        @DisplayName("message필드는 null일 수 없다.")
        void chatMessageNullTest() throws Exception {
            //given
            ChatMessage.ChatMessageBuilder builder = ChatMessage.builder()
                .chatRoomUser(ChatRoomUserFixture.createDefaultChatRoomUser());
            //expected
            Assertions.assertThrows(IllegalArgumentException.class, builder::build);
        }

        @Test
        @DisplayName("message필드는 빈 문자열일 수 없다.")
        void chatMessageRelationNotNullableTest() throws Exception {
            //given
            ChatMessage.ChatMessageBuilder builder = ChatMessage.builder()
                .message("");
            //expected
            Assertions.assertThrows(IllegalArgumentException.class, builder::build);
        }
    }

    @Nested
    @DisplayName("ChatRoom 엔티티 테스트")
    class ChatRoomTest {

        @Test
        @DisplayName("채팅방의 이름은 20자를 넘어선 안된다.")
        void roomNameValidationTest() throws Exception {
            //given
            ChatRoom.ChatRoomBuilder builder = ChatRoom.builder()
                .roomName(ChatRoomFixture.INVALID_LENGTH_ROOM_NAME);
            //when&expected
            Assertions.assertThrows(InvalidValueException.class, builder::build);
        }
    }
}