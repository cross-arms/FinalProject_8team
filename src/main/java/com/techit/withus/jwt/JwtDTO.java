package com.techit.withus.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class JwtDTO
{
    private String accessToken;
    private String refreshToken;
}
