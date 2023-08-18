package com.techit.withus.web.users.controller;

import com.techit.withus.security.SecurityService;
import com.techit.withus.web.common.domain.dto.ResultDTO;
import com.techit.withus.web.common.domain.dto.TokenDTO;
import com.techit.withus.web.users.domain.dto.LogInDTO;
import com.techit.withus.web.users.service.LogInService;
import lombok.RequiredArgsConstructor;
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
