package com.techit.withus.redis.hashes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash(value = "email", timeToLive = 60 * 30) // 60초 30분
public class Email
{
    // 이메일을 키 값으로, 인증번호를 밸류 값으로
    @Id
    private String email;
    private String code;
}
