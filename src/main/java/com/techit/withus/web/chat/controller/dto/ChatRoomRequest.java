package com.techit.withus.web.chat.controller.dto;

import java.util.List;

import com.techit.withus.common.exception.ErrorCode;
import com.techit.withus.common.exception.InvalidValueException;
import com.techit.withus.web.chat.domain.ChatRoom;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomRequest {

    @NotBlank
    private String roomName;
    @NotNull
    private List<Long> roomMemberIds;

    @Builder
    public ChatRoomRequest(String roomName, List<Long> roomMemberIds) {
        validateRoomMemberIds(roomMemberIds);
        this.roomName = roomName;
        this.roomMemberIds = roomMemberIds;
    }

    private void validateRoomMemberIds(List<Long> roomMemberIds){
        if(roomMemberIds.isEmpty()){
            throw new InvalidValueException(ErrorCode.INVALID_ROOM_MEMBER);
        }
    }

    public ChatRoom toChatRoom(){
        return ChatRoom.builder()
            .roomName(roomName)
            .build();
    }
}
