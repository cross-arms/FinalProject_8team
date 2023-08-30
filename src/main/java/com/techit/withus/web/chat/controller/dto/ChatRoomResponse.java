package com.techit.withus.web.chat.controller.dto;

import com.techit.withus.web.chat.domain.ChatRoom;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomResponse {
    private Long id;
    private String roomName;

    @Builder
    public ChatRoomResponse(Long id, String roomName) {
        this.id = id;
        this.roomName = roomName;
    }

    public static ChatRoomResponse from(ChatRoom chatRoom){
        return ChatRoomResponse.builder()
            .id(chatRoom.getId())
            .roomName(chatRoom.getRoomName())
            .build();
    }
}
