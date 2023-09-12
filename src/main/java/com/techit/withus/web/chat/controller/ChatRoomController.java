package com.techit.withus.web.chat.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techit.withus.security.SecurityUser;
import com.techit.withus.web.chat.controller.dto.ChatRoomRequest;
import com.techit.withus.web.chat.controller.dto.ChatRoomResponse;
import com.techit.withus.web.chat.controller.dto.RoomUpdateRequest;
import com.techit.withus.web.chat.service.ChatRoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping("/rooms")
    public ResponseEntity<Void> createRoom(@RequestBody ChatRoomRequest request){
        return ResponseEntity
            .created(URI.create("/rooms/" + chatRoomService.saveChatRoom(request)))
            .build();
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoomResponse>> getChatRooms(
        @AuthenticationPrincipal SecurityUser user
    ){
        return ResponseEntity.ok(chatRoomService.readChatRooms(user.getUsername()));
    }

    @PostMapping("/rooms/{roomId}")
    public ResponseEntity<Void> update(@PathVariable Long roomId, @RequestBody RoomUpdateRequest request){
        chatRoomService.updateChatRoom(roomId, request);
        return ResponseEntity.noContent().build();
    }
}
