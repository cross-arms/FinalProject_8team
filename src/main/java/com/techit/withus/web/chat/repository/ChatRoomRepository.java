package com.techit.withus.web.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techit.withus.common.exception.EntityNotFoundException;
import com.techit.withus.common.exception.ErrorCode;
import com.techit.withus.web.chat.domain.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    default ChatRoom findByIdOrThrow(Long id){
        return findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHATROOM_NOT_EXIST, id));
    }
}

