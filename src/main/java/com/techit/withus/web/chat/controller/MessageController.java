package com.techit.withus.web.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import com.techit.withus.security.SecurityUser;
import com.techit.withus.web.chat.controller.dto.MessageRequest;
import com.techit.withus.web.chat.service.MessageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    @MessageMapping("/messages")
    public void sendMessage(
        @Payload MessageRequest messageRequest,
        @AuthenticationPrincipal SecurityUser user
    ){
        messageService.save(messageRequest, user.getUsername());
        messagingTemplate.convertAndSend(String.format("/sub/rooms/%d", messageRequest.getRoomId()), messageRequest.getMessage());
    }
}
