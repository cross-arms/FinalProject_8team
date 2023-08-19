package com.techit.withus.redis.hashes;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@AllArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24) // 60초 60분 24시간
public class RefreshToken
{
    @Id @Indexed
    private String refreshToken;
    private Long userId;
}
