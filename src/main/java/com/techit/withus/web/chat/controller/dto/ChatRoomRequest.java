package com.techit.withus.web.chat.controller.dto;

import java.util.List;

import com.techit.withus.web.chat.domain.ChatRoom;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomRequest {
    private String roomName;
    private List<Long> roomMemberIds;

    @Builder
    public ChatRoomRequest(String roomName, List<Long> roomMemberIds) {
        this.roomName = roomName;
        this.roomMemberIds = roomMemberIds;
    }

    public ChatRoom toChatRoom(){
        return ChatRoom.builder()
            .roomName(roomName)
            .build();
    }
}
