package com.techit.withus.web.chat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techit.withus.web.chat.controller.dto.ChatRoomRequest;
import com.techit.withus.web.chat.controller.dto.ChatRoomResponse;
import com.techit.withus.web.chat.controller.dto.RoomUpdateRequest;
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

    @Transactional
    public void updateChatRoom(Long roomId, RoomUpdateRequest request){
        ChatRoom chatRoom = chatRoomRepository.findByIdOrThrow(roomId);
        List<Users> roomUsers = userRepository.findByUserIdIn(request.getMemberIds());
        chatRoom.updateChatRoom(request.getRoomName(), roomUsers);
    }

    public List<ChatRoomResponse> readChatRooms(String userName){
        List<ChatRoomUser> chatRoomUsers = chatRoomUserRepository.findChatRoomUserByUserName(userName);
        return chatRoomUsers
            .stream()
            .map(chatRoomUser -> ChatRoomResponse.from(chatRoomUser.getChatRoom())).toList();
    }
}
