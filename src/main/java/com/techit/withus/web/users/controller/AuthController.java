package com.techit.withus.web.users.controller;

import com.techit.withus.common.dto.ResultDTO;
import com.techit.withus.web.users.domain.dto.EmailDTO;
import com.techit.withus.web.users.domain.dto.LogInDTO;
import com.techit.withus.web.users.domain.dto.SignUpDTO;
import com.techit.withus.web.users.service.AuthService;
import com.techit.withus.web.users.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController
{
    private final AuthService authService;
    private final EmailService emailService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody SignUpDTO signUpDTO)
    {
        authService.signUp(signUpDTO);
        return ResponseEntity.ok().build();
    }

    // 로그인
    @PostMapping("/log-in")
    public ResponseEntity<Void> logIn(@RequestBody LogInDTO logInDTO,
                                      HttpServletResponse response)
    {
        authService.logIn(logInDTO, response);
        return ResponseEntity.ok().build();
    }

    // 로그아웃
    @PostMapping("/log-out")
    public ResponseEntity<Void> logOut(HttpServletRequest request)
    {
        authService.logOut(request);
        return ResponseEntity.ok().build();
    }

    // 이메일을 입력 받으면 인증 번호를 전송한다.
    @PostMapping("/email")
    public ResponseEntity<Void> sendEmail(@RequestBody EmailDTO emailDTO) throws MessagingException
    {
        emailService.sendEmail(emailDTO);
        return ResponseEntity.ok().build();
    }

    // 이메일과 인증 번호를 입력 받아서 인증 절차를 진행한다.
    @PatchMapping("/email")
    public ResponseEntity<Void> checkEmailAndCode(@RequestBody EmailDTO emailDTO)
    {
        emailService.validateCode(emailDTO);
        return ResponseEntity.ok().build();
    }
}
