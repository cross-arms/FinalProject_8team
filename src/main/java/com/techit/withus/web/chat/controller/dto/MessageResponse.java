package com.techit.withus.web.chat.controller.dto;

import java.time.LocalDateTime;

import com.techit.withus.web.chat.domain.ChatMessage;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageResponse {
    private Long id;
    private String message;
    private LocalDateTime createdAt;

    @Builder
    public MessageResponse(Long id, String message, LocalDateTime createdAt) {
        this.id = id;
        this.message = message;
        this.createdAt = createdAt;
    }

    public static MessageResponse from(ChatMessage message){
        return MessageResponse.builder()
            .id(message.getId())
            .message(message.getMessage())
            .createdAt(message.getCreateAt())
            .build();
    }
}
