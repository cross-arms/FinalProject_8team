package com.techit.withus.web.users.domain.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignUpDTO
{
    private String username;
    private String password;
    private String phone;
}
