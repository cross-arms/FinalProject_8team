package com.techit.withus.web.chat.service;

import static com.techit.withus.common.fixture.chat.ChatMessageFixture.*;
import static com.techit.withus.common.fixture.chat.ChatRoomFixture.*;
import static com.techit.withus.common.fixture.chat.ChatRoomUserFixture.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import com.techit.withus.common.exception.EntityNotFoundException;
import com.techit.withus.common.fixture.chat.ChatRoomUserFixture;
import com.techit.withus.common.fixture.users.UserFixture;
import com.techit.withus.web.chat.controller.dto.ChatRoomResponse;
import com.techit.withus.web.chat.domain.ChatMessage;
import com.techit.withus.web.chat.domain.ChatRoom;
import com.techit.withus.web.chat.repository.ChatMessageRepository;
import com.techit.withus.web.chat.repository.ChatRoomRepository;
import com.techit.withus.web.chat.repository.ChatRoomUserRepository;
import com.techit.withus.web.notification.controller.dto.NotificationEvent;
import com.techit.withus.web.users.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTest {
    private static final Long NON_EXIST_ROOM_ID = 99999999L;
    private static final Long EXIST_ROOM_ID = 1L;
    private static final String NON_EXIST_USERNAME = "fkdneiofw";
    private static final String EXIST_USERNAME = "user1";
    private static final int RANDOM_SIZE = 3;
    @Mock
    ChatRoomUserRepository chatRoomUserRepository;
    @Mock
    ChatMessageRepository chatMessageRepository;
    @Mock
    ChatRoomRepository chatRoomRepository;
    @Mock
    ApplicationEventPublisher publisher;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    MessageService messageService;
    @InjectMocks
    ChatRoomService chatRoomService;

    @Nested
    @DisplayName("채팅 메시지 서비스 관련 테스트")
    class MessageServiceTest{
        @Test
        @DisplayName("존재하지 않는 채팅방의 id로 메시지를 저장할 수 없습니다.")
        void invalidRoomMessageSaveTest() throws Exception {
            //given
            when(chatRoomUserRepository.findByChatRoomIdAndUsername(NON_EXIST_ROOM_ID, EXIST_USERNAME)).thenThrow(
                EntityNotFoundException.class);
            //expected
            Assertions.assertThrows(EntityNotFoundException.class, () -> messageService.save(createMessageRequest(NON_EXIST_ROOM_ID), EXIST_USERNAME));
        }

        @Test
        @DisplayName("존재하지 않는 회원 이름으로 메시지를 저장할 수 없습니다.")
        void invalidUserMessageSaveTest() throws Exception {
            //given
            when(chatRoomUserRepository.findByChatRoomIdAndUsername(EXIST_ROOM_ID, NON_EXIST_USERNAME)).thenThrow(
                EntityNotFoundException.class);
            //when
            Assertions.assertThrows(EntityNotFoundException.class, () -> messageService.save(createMessageRequest(EXIST_ROOM_ID), NON_EXIST_USERNAME));
        }

        @Test
        @DisplayName("존재하는 회원의 이름과 roomid로 메시지를 저장할 수 있다.")
        void MessageSaveSuccessTest() throws Exception {
            //given
            when(chatRoomUserRepository.findChatRoomUserByChatRoomId(EXIST_ROOM_ID)).thenReturn(createChatRoomUsersWithMultipleUsers(EXIST_USERNAME, 5));
            when(chatMessageRepository.save(any(ChatMessage.class))).thenReturn(createChatMessageWithId(TEST_ID_A));
            doNothing().when(publisher).publishEvent(any(NotificationEvent.class));
            //when
            messageService.save(createMessageRequest(EXIST_ROOM_ID), EXIST_USERNAME);
            //then
            verify(chatMessageRepository, times(1)).save(any(ChatMessage.class));
        }
    }

    @Nested
    @DisplayName("채팅 방 서비스 관련 테스트")
    class ChatRoomServiceTest{
        @Test
        @DisplayName("사용자가 소속한 채팅방을 조회할 수 있다.")
        void findChatRoomByUsernameTest() throws Exception {
            //given
            when(chatRoomUserRepository.findChatRoomUserByUserName(EXIST_USERNAME))
                .thenReturn(ChatRoomUserFixture.createChatRoomUsersWithId(RANDOM_SIZE));
            //when
            List<ChatRoomResponse> chatRoomResponses = chatRoomService.readChatRooms(EXIST_USERNAME);
            //then
            org.assertj.core.api.Assertions.assertThat(chatRoomResponses).hasSize(RANDOM_SIZE);
        }

        @Test
        @DisplayName("사용자를 포함한 여러 회원들의 정보를 통해 채팅방을 생성할 수 있다.")
        void saveChatRoomTest() throws Exception {
            //given
            when(userRepository.findByUserIdIn(List.of(1L, 2L, 3L, 4L))).thenReturn(UserFixture.createUsersList(4));
            when(chatRoomRepository.save(any(ChatRoom.class))).thenReturn(createDefaultChatRoom());
            //when
            chatRoomService.saveChatRoom(createChatRoomRequest());
            //then
            verify(chatRoomRepository, times(1)).save(any(ChatRoom.class));
        }

        @Test
        @DisplayName("사용자는 방이름을 수정하고, 방의 구성원을 추가할 수 있다.")
        void updateChatRoomTest() throws Exception {
            //given
            when(chatRoomRepository.findByIdOrThrow(TEST_CHATROOM_ID_A)).thenReturn(createTestAChatRoomWithId(TEST_CHATROOM_ID_A));
            when(userRepository.findByUserIdIn(List.of(1L, 2L, 3L, 4L))).thenReturn(UserFixture.createUsersList(4));
            //when & expected
            chatRoomService.updateChatRoom(TEST_CHATROOM_ID_A, createRoomUpdateRequest());
        }
    }
}
