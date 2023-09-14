package com.techit.withus.web.chat.service;

import java.util.List;

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
        List<Long> receiversIds = chatRoomUsers.stream()
            .filter(user -> !username.equals(user.getUser().getEmail()))
            .map(user -> user.getUser().getUserId())
            .toList();
        chatMessage.publishMessageEvent(publisher, receiversIds);
        return chatMessageRepository.save(chatMessage).getId();
    }

    public Slice<ChatMessage> readMessages(Long roomId, String username, Pageable pageable){
        var chatRoomUser = chatRoomUserRepository.findByChatRoomIdAndUsername(roomId, username)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHATROOM_USER_NOT_EXIST));
        return chatMessageRepository.findChatMessagesByChatRoomUser(chatRoomUser, pageable);
    }
}
