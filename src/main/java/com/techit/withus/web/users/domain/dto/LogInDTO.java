package com.techit.withus.web.users.domain.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LogInDTO
{
    private String email;
    private String password;
}
