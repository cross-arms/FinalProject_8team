package com.techit.withus.web.users.controller;

import com.techit.withus.security.SecurityUser;
import com.techit.withus.common.dto.ResultDTO;
import com.techit.withus.web.users.domain.dto.EmailDTO;
import com.techit.withus.web.users.service.EmailService;
import com.techit.withus.web.users.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController
{
    private final UserService userService;
    private final EmailService emailService;

    // 이메일을 입력 받으면 인증 번호를 전송한다.
    @PostMapping("/verification")
    public ResultDTO sendEmail(@RequestBody EmailDTO emailDTO) throws MessagingException
    {
        emailService.sendEmail(emailDTO);
        return ResultDTO
                .builder()
                .message("인증번호를 발송했습니다.")
                .build();
    }

    // 이메일과 인증 번호를 입력 받아서 인증 절차를 진행한다.
    @PatchMapping("/verification")
    public ResultDTO checkEmailAndCode(@AuthenticationPrincipal SecurityUser user,
                                       @RequestBody EmailDTO emailDTO)
    {
        emailService.validateCode(emailDTO);
        userService.updateEmailAndRole(user, emailDTO);
        return ResultDTO
                .builder()
                .message("이메일 인증을 성공적으로 완료했습니다.")
                .build();
    }


}
