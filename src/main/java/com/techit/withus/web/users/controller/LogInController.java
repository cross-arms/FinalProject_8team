package com.techit.withus.web.users.controller;

import com.techit.withus.security.SecurityService;
import com.techit.withus.web.common.domain.dto.ResultDTO;
import com.techit.withus.web.common.domain.dto.TokenDTO;
import com.techit.withus.web.users.domain.dto.LogInDTO;
import com.techit.withus.web.users.service.LogInService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LogInController
{
    private final LogInService logInService;

    /**
     * 로그인 요청을 Service에 전달하는 메서드이다.
     * Service로부터 전달받은 토큰의 정보를 요청에 대한 응답으로 전달한다.
     */
    @PostMapping
    public ResultDTO login(@RequestBody LogInDTO logInDTO)
    {
        TokenDTO tokenDTO = logInService.login(logInDTO);
        return ResultDTO
                .builder()
                .data(tokenDTO)
                .build();
    }
}
