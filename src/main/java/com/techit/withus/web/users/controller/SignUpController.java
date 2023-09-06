package com.techit.withus.web.users.controller;

import com.techit.withus.common.dto.ResultDTO;
import com.techit.withus.web.users.domain.dto.SignUpDTO;
import com.techit.withus.web.users.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
    @RequestMapping("/sign-up")
public class SignUpController
{
    private final SignUpService signUpService;

    @PostMapping
    public ResultDTO signUp(@RequestBody SignUpDTO signUpDTO)
    {
        signUpService.signUp(signUpDTO);
        return ResultDTO
                .builder()
                .message("회원가입이 정상적으로 완료되었습니다.")
                .build();
    }
}
