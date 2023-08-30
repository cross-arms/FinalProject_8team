package com.techit.withus.web.chat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.techit.withus.common.exception.EntityNotFoundException;
import com.techit.withus.common.exception.ErrorCode;
import com.techit.withus.web.chat.domain.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    default ChatRoom findByIdOrThrow(Long id){
        return findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHATROOM_NOT_EXIST, id));
    }

    @Query("select cr from ChatRoom cr join fetch cr.chatRoomUsers cru where cr.id=:roomId")
    Optional<ChatRoom> findByIdJoinFetch(Long roomId);
}

