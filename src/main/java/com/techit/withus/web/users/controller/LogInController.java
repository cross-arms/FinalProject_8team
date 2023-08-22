package com.techit.withus.web.users.controller;

import com.techit.withus.web.common.domain.dto.ResultDTO;
import com.techit.withus.jwt.JwtDTO;
import com.techit.withus.web.users.domain.dto.LogInDTO;
import com.techit.withus.web.users.service.LogInService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/log-in")
public class LogInController
{
    private final LogInService logInService;

    /**
     * 로그인 요청을 Service에 전달하는 메서드이다.
     * Service로부터 전달받은 토큰의 정보를 요청에 대한 응답으로 전달한다.
     */
    @PostMapping
    public ResultDTO logIn(@RequestBody LogInDTO logInDTO)
    {
        JwtDTO jwtDTO = logInService.logIn(logInDTO);
        return ResultDTO
                .builder()
                .data(jwtDTO)
                .build();
    }
}
