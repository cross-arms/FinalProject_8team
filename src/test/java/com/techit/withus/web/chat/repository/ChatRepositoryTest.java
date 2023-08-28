package com.techit.withus.web.chat.repository;

import static com.techit.withus.common.ChatMessageFixture.*;
import static com.techit.withus.common.ChatRoomFixture.*;

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
    @DisplayName("채팅방 id와 username으로 채팅방을 검색할 수 있다.")
    void findByChatRoomIdAndUsernameTest() throws Exception {
        //given
        Optional<ChatRoomUser> optional = chatRoomUserRepository.findByChatRoomIdAndUsername(TEST_CHATROOM_ID_A, TEST_RANDOM_USERNAME);
        //expected
        Assertions.assertThat(optional).isNotEmpty();
    }

    @Test
    @DisplayName("userId로 user가 소속된 채팅방을 모두 가져올 수 있다.")
    void findChatRoomUserByuserIdTest() throws Exception {
        //given
        PageRequest pageRequest = PageRequest.of(0, 5);
        Slice<ChatRoomUser> chatRoomUsers = chatRoomUserRepository.findChatRoomUserByMemberId(TEST_RANDOM_USERNAME, pageRequest);
        //expected
        Assertions.assertThat(chatRoomUsers).isNotEmpty();
        Assertions.assertThat(chatRoomUsers.getContent().get(0).getUser().getUserId()).isEqualTo(RANDOM_MEMBER_ID);
    }
}