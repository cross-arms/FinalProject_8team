package com.techit.withus.web.chat.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.techit.withus.common.fixture.AcceptanceTest;
import com.techit.withus.common.fixture.chat.ChatRoomFixture;
import com.techit.withus.security.SecurityUser;
import com.techit.withus.web.chat.controller.dto.ChatRoomResponse;
import com.techit.withus.web.chat.service.ChatRoomService;

@WithMockUser(username = "user1")
@Transactional
class ChatControllerTest extends AcceptanceTest {
    @MockBean
    ChatRoomService chatRoomService;
    @Test
    @DisplayName("채팅 방 생성 요청")
    void createChatRoomTest() throws Exception {
        //given

        ResultActions action = mockMvc.perform(post("/rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(ChatRoomFixture.createChatRoomRequest())));
        //expected
        action.andDo(print())
            .andExpect(status().isCreated())
            .andExpect(redirectedUrl("/rooms/1"));
    }

    @Test
    @DisplayName("사용자가 속한 채팅방 요청")
    void readChatRoomsTest() throws Exception {
        //given
        UserDetails userDetails = SecurityUser.builder().username("user1").password("password").role("USER").build();
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, "password", userDetails.getAuthorities());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);
        ChatRoomResponse room1 = ChatRoomResponse.builder().id(1L).roomName("Room 1").build();
        ChatRoomResponse room2 = ChatRoomResponse.builder().id(2L).roomName("Room 2").build();
        List<ChatRoomResponse> chatRooms = Arrays.asList(room1, room2);
        Mockito.when(chatRoomService.readChatRooms("user1")).thenReturn(chatRooms);
        ResultActions action = mockMvc.perform(get("/rooms").principal(auth));
        //expected
        action.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(2));
    }
}
