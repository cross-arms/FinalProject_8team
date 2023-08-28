package com.techit.withus.web.chat.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.techit.withus.common.exception.EntityNotFoundException;
import com.techit.withus.common.exception.ErrorCode;
import com.techit.withus.web.chat.domain.ChatRoomUser;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {

    @Query("select cru from ChatRoomUser cru join fetch cru.chatRoom where cru.user.username=:userName order by cru.createAt desc")
    Slice<ChatRoomUser> findChatRoomUserByMemberId(String userName, Pageable pageable);

    @Query("select cru from ChatRoomUser cru join fetch cru.user u join fetch cru.chatRoom cr where cr.id=:chatRoomId and u.username=:userName")
    Optional<ChatRoomUser> findByChatRoomIdAndUsername(Long chatRoomId, String userName);

    default ChatRoomUser findByIdOrThrow(Long roomId){
        return findById(roomId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CHATROOM_NOT_EXIST));
    }
}
