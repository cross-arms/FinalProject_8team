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
    private String email;
    private String password;
}
