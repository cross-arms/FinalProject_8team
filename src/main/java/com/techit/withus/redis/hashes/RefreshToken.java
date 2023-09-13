package com.techit.withus.redis.hashes;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24) // 60초 1시간 하루
public class RefreshToken
{
    // 이메일을 key, 리프레시 토큰을 value
    @Id
    private String email;
    private String refreshToken;
}
