package com.techit.withus.web.chat.controller.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomUpdateRequest {
    @NotBlank
    private String roomName;
    private List<Long> memberIds;

    @Builder
    public RoomUpdateRequest(Long roomId, String roomName, List<Long> memberIds) {
        this.roomName = roomName;
        this.memberIds = memberIds;
    }
}
