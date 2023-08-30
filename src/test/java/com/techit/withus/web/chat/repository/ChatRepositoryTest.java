package com.techit.withus.web.chat.repository;

import static com.techit.withus.common.fixture.chat.ChatMessageFixture.*;
import static com.techit.withus.common.fixture.chat.ChatRoomFixture.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.techit.withus.common.exception.EntityNotFoundException;
import com.techit.withus.common.exception.ErrorCode;
import com.techit.withus.web.chat.domain.ChatMessage;
import com.techit.withus.web.chat.domain.ChatRoomUser;


@Transactional
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ChatRepositoryTest {
    private final Long RANDOM_MEMBER_ID = 1L;
    private final String TEST_RANDOM_USERNAME = "user1";
    private static final Long NON_EXIST_ROOM_ID = 99999999L;
    private static final Long EXIST_ROOM_ID = 1L;
    private static final String NON_EXIST_USERNAME = "fkdneiofw";
    private static final String EXIST_USERNAME = "user1";

    @Autowired
    ChatMessageRepository chatMessageRepository;
    @Autowired
    ChatRoomUserRepository chatRoomUserRepository;
    @Autowired
    ChatRoomRepository chatRoomRepository;


    @Test
    @DisplayName("ChatRoomUser를 조건으로 message를 Slice로 조회할 수 있다.")
    void findChatMessagesByChatRoomUserTest() throws Exception {
        //given
        ChatRoomUser chatRoomUser = chatRoomUserRepository.findById(RANDOM_MEMBER_ID)
            .orElseThrow(() -> new EntityNotFoundException(
                ErrorCode.CHATROOM_USER_NOT_EXIST));
        PageRequest pageRequest = PageRequest.of(TEST_PAGE_NUMBER, TEST_PAGING_SIZE, Sort.by(Sort.Direction.DESC, "createAt"));
        //when
        Slice<ChatMessage> page = chatMessageRepository.findChatMessagesByChatRoomUser(
            chatRoomUser, pageRequest);
        System.out.println(page.getContent().get(1).getCreateAt());
        System.out.println(page.getContent().get(2).getCreateAt());
        //then
        Assertions.assertThat(page.getSize()).isEqualTo(TEST_PAGING_SIZE);
        Assertions.assertThat(page.getContent().get(0).getCreateAt()).isAfter(page.getContent().get(1).getCreateAt());
        Assertions.assertThat(page.getContent().get(1).getCreateAt()).isAfter(page.getContent().get(2).getCreateAt());
    }

    @Test
    @DisplayName("존재하지 않는 채팅방의 id로 메시지를 저장할 수 없습니다.")
    void setNonExistRoomIdMessageSaveTest() throws Exception {
        //given & expected
        org.junit.jupiter.api.Assertions.assertThrows(EntityNotFoundException.class, () -> chatRoomUserRepository.findByChatRoomIdAndUsername(NON_EXIST_ROOM_ID,
            EXIST_USERNAME).orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHATROOM_USER_NOT_EXIST)));
    }

    @Test
    @DisplayName("존재하지 않는 회원 이름으로 메시지를 저장할 수 없습니다.")
    void setNonExistUsernameMessageSaveTest() throws Exception {
        //given & expected
        org.junit.jupiter.api.Assertions.assertThrows(EntityNotFoundException.class, () -> chatRoomUserRepository.findByChatRoomIdAndUsername(EXIST_ROOM_ID,
            NON_EXIST_USERNAME).orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHATROOM_USER_NOT_EXIST)));
    }

    @Test
    @DisplayName("채팅방 id와 username으로 채팅방을 검색할 수 있다.")
    void findByChatRoomIdAndUsernameTest() throws Exception {
        //given
        Optional<ChatRoomUser> optional = chatRoomUserRepository.findByChatRoomIdAndUsername(TEST_CHATROOM_ID_A, TEST_RANDOM_USERNAME);
        //expected
        Assertions.assertThat(optional).isNotEmpty();
    }

    @Test
    @DisplayName("user이름으로 user가 소속된 채팅방을 모두 가져올 수 있다.")
    void findChatRoomUserByuserIdTest() throws Exception {
        //given
        List<ChatRoomUser> chatRoomUsers = chatRoomUserRepository.findChatRoomUserByUserName(TEST_RANDOM_USERNAME);
        //expected
        Assertions.assertThat(chatRoomUsers).isNotEmpty();
        Assertions.assertThat(chatRoomUsers.get(0).getUser().getUserId()).isEqualTo(RANDOM_MEMBER_ID);
    }
}