package com.techit.withus.web.users.controller;

import com.techit.withus.common.dto.ResultDTO;
import com.techit.withus.jwt.JwtDTO;
import com.techit.withus.web.users.domain.dto.LogInDTO;
import com.techit.withus.web.users.service.LogInService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/log-in")
public class LogInController
{
    private final LogInService logInService;

    @PostMapping
    public ResultDTO logIn(@RequestBody LogInDTO logInDTO)
    {
        JwtDTO jwtDTO = logInService.logIn(logInDTO);
        return ResultDTO
                .builder()
                .data(jwtDTO)
                .build();
    }

    @GetMapping("/test")
    public String logIn()
    {
        return "oauth";
    }
}
