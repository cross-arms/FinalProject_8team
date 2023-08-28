package com.techit.withus.web.users.domain.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailDTO
{
    private String address; // 인증번호를 받을 이메일 주소
    private String code; // 해당 이메일 주소로 발송된 인증번호
}
