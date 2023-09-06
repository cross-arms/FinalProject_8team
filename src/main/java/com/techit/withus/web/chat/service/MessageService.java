package com.techit.withus.web.chat.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techit.withus.common.exception.EntityNotFoundException;
import com.techit.withus.common.exception.ErrorCode;
import com.techit.withus.web.chat.controller.dto.MessageRequest;
import com.techit.withus.web.chat.domain.ChatMessage;
import com.techit.withus.web.chat.domain.ChatRoomUser;
import com.techit.withus.web.chat.repository.ChatMessageRepository;
import com.techit.withus.web.chat.repository.ChatRoomUserRepository;
import com.techit.withus.web.users.domain.entity.Users;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public Long save(MessageRequest request, String username){
        List<ChatRoomUser> chatRoomUsers = chatRoomUserRepository.findChatRoomUserByChatRoomId(request.getRoomId());
        var foundChatRoomUser = chatRoomUsers.stream()
            .filter(chatRoomUser -> username.equals(chatRoomUser.getUser().getEmail()))
            .findFirst()
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.INVALID_ROOM_MEMBER));
        ChatMessage chatMessage = request.toChatMessage();
        chatMessage.associateChatRoomUser(foundChatRoomUser);
        List<Users> receivers = chatRoomUsers.stream()
            .map(ChatRoomUser::getUser)
            .filter(user -> !username.equals(user.getEmail()))
            .collect(Collectors.toList());
        chatMessage.publishMessageEvent(publisher, receivers);
        return chatMessageRepository.save(chatMessage).getId();
    }

    public Slice<ChatMessage> readMessages(Long roomId, String username, Pageable pageable){
        var chatRoomUser = chatRoomUserRepository.findByChatRoomIdAndUsername(roomId, username)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHATROOM_USER_NOT_EXIST));
        return chatMessageRepository.findChatMessagesByChatRoomUser(chatRoomUser, pageable);
    }
}
