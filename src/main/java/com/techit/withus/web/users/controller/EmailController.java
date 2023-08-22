package com.techit.withus.web.users.controller;

import com.techit.withus.web.common.domain.dto.ResultDTO;
import com.techit.withus.web.users.domain.dto.EmailDTO;
import com.techit.withus.web.users.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController
{
    private final EmailService emailService;

    // 이메일을 입력 받으면 인증 번호를 전송한다.
    @GetMapping("/verification")
    public ResultDTO sendEmail(@RequestBody EmailDTO emailDTO) throws MessagingException
    {
        emailService.sendEmail(emailDTO);
        return ResultDTO
                .builder()
                .message("인증번호를 발송했습니다.")
                .build();
    }

    /**
     * 이메일과 인증 번호를 입력 받고 인증 절차를 진행한다.
     * 1)
     */
    @PostMapping("/verification")
    public ResultDTO checkEmailAndCode(@RequestBody EmailDTO emailDTO)
    {
        return ResultDTO
                .builder()
                .message("")
                .build();
    }


}
