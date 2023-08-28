package com.techit.withus.web.chat.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techit.withus.web.chat.controller.dto.ChatRoomRequest;
import com.techit.withus.web.chat.controller.dto.ChatRoomResponse;
import com.techit.withus.web.chat.domain.ChatRoom;
import com.techit.withus.web.chat.domain.ChatRoomUser;
import com.techit.withus.web.chat.repository.ChatRoomRepository;
import com.techit.withus.web.chat.repository.ChatRoomUserRepository;
import com.techit.withus.web.users.domain.entity.Users;
import com.techit.withus.web.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;

    @Transactional
    public Long saveChatRoom(ChatRoomRequest request){
        List<Users> roomUsers = userRepository.findByUserIdIn(request.getRoomMemberIds());
        ChatRoom chatRoom = request.toChatRoom();
        chatRoom.addUsers(roomUsers);
        return chatRoomRepository.save(chatRoom).getId();
    }

    @Transactional
    public void deleteChatRoom(Long roomId){
        chatRoomRepository.deleteById(roomId);
    }

    public Slice<ChatRoomResponse> readChatRooms(String userName, Pageable pageable){
        Slice<ChatRoomUser> chatRoomUsers = chatRoomUserRepository.findChatRoomUserByMemberId(userName, pageable);
        boolean hasNext = chatRoomUsers.hasNext();
        List<ChatRoomResponse> chatRoomResponses = chatRoomUsers
            .getContent()
            .stream()
            .map(chatRoomUser -> ChatRoomResponse.from(chatRoomUser.getChatRoom())).toList();
        return new SliceImpl<>(chatRoomResponses, pageable, hasNext);
    }
}
