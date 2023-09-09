package com.techit.withus.redis.hashes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash(value = "blackList", timeToLive = 60 * 5) // 60초 5분
public class BlackList
{
    // 이메일을 key, 리프레시 토큰을 value
    @Id
    private String accessToken;
    private final String logout = "logout";
}
