package com.techit.withus.web.chat.controller.dto;

import com.techit.withus.web.chat.domain.ChatMessage;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageRequest {
    private Long roomId;
    private String message;
    private String time;

    @Builder
    public MessageRequest(Long roomId, String message, String time) {
        this.roomId = roomId;
        this.message = message;
        this.time = time;
    }

    public ChatMessage toChatMessage(){
        return ChatMessage.builder()
            .message(message)
            .build();
    }
}
