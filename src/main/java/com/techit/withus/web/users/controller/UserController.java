package com.techit.withus.web.users.controller;

import com.techit.withus.web.common.domain.dto.ResultDTO;
import com.techit.withus.web.users.domain.dto.SignUpDTO;
import com.techit.withus.web.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController
{
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResultDTO signUp(SignUpDTO signUpDTO)
    {
        return ResultDTO
                .builder()
                .build();
    }
}
