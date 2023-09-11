package com.techit.withus.web.chat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.techit.withus.web.chat.domain.ChatRoomUser;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {

    @Query("select cru from ChatRoomUser cru join fetch cru.chatRoom where cru.user.email=:email")
    List<ChatRoomUser> findChatRoomUserByUserName(String email);

    @Query("select cru from ChatRoomUser cru join fetch cru.user where cru.chatRoom.id=:chatRoomId")
    List<ChatRoomUser> findChatRoomUserByChatRoomId(Long chatRoomId);

    @Query("select cru from ChatRoomUser cru join fetch cru.user u join fetch cru.chatRoom cr where cr.id=:chatRoomId and u.username=:userName")
    Optional<ChatRoomUser> findByChatRoomIdAndUsername(Long chatRoomId, String userName);
}
