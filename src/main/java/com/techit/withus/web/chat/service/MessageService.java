package com.techit.withus.web.chat.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techit.withus.common.exception.EntityNotFoundException;
import com.techit.withus.common.exception.ErrorCode;
import com.techit.withus.web.chat.controller.dto.MessageRequest;
import com.techit.withus.web.chat.domain.ChatMessage;
import com.techit.withus.web.chat.repository.ChatMessageRepository;
import com.techit.withus.web.chat.repository.ChatRoomUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;

    @Transactional
    public Long save(MessageRequest request, String username){
        var chatRoomUser = chatRoomUserRepository
            .findByChatRoomIdAndUsername(request.getRoomId(), username)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHATROOM_USER_NOT_EXIST));
        ChatMessage chatMessage = request.toChatMessage();
        chatMessage.associateChatRoomUser(chatRoomUser);
        return chatMessageRepository.save(chatMessage).getId();
    }

    public Slice<ChatMessage> readMessages(Long roomId, String username, Pageable pageable){
        var chatRoomUser = chatRoomUserRepository.findByChatRoomIdAndUsername(roomId, username)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHATROOM_USER_NOT_EXIST));
        return chatMessageRepository.findChatMessagesByChatRoomUser(chatRoomUser, pageable);
    }
}
