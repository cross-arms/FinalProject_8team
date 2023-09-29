package com.techit.withus.web.chat.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import com.techit.withus.security.SecurityUser;
import com.techit.withus.web.chat.controller.dto.MessageRequest;
import com.techit.withus.web.chat.controller.dto.MessageResponse;
import com.techit.withus.web.chat.domain.ChatMessage;
import com.techit.withus.web.chat.service.MessageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/message")
    public void sendMessage(
        @Payload MessageRequest messageRequest,
        @AuthenticationPrincipal SecurityUser user
    ){
        messageService.save(messageRequest, "user1@example.com");
        messagingTemplate.convertAndSend("/topic/room." + messageRequest.getRoomId(), messageRequest);
    }

    @SubscribeMapping("/room.{roomId}")
    public ResponseEntity<Map<String, Object>> subscriptionMessage(
        @DestinationVariable Long roomId,
        @AuthenticationPrincipal SecurityUser user,
        @RequestParam(defaultValue = "1") int page
    ){
        PageRequest pageRequest = PageRequest.of(page - 1, 20, Sort.by(Sort.Direction.DESC, "createdAt"));
        Slice<ChatMessage> chatMessages = messageService.readMessages(roomId, user.getUsername(), pageRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("messages", chatMessages.map(MessageResponse::from));
        response.put("enteredAt", LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}
