package com.techit.withus.web.users.service;

import com.techit.withus.redis.hashes.Email;
import com.techit.withus.redis.repository.EmailRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;

import java.util.Optional;

@DisplayName("Redis 이메일 인증 테스트")
@DataRedisTest
class EmailServiceTest
{
    @Autowired
    private EmailRepository emailRepository;

    @Test
    @DisplayName("KEY: 이메일, VALUE: 인증번호")
    public void verifyEmail()
    {
        // given
        String email = "gyeongnamdev@gmail.com";
        String code = "49782012";

        Email emailVerification = new Email(email, code);

        // when
        emailRepository.save(emailVerification);

        // then
        Assertions.assertTrue(emailRepository.existsById(email));
        Assertions.assertFalse(emailRepository.existsById("gyeongnam@gmail.com"));
        Assertions.assertEquals(emailRepository.findById(email).get().getCode(), code);
        Assertions.assertNotEquals(emailRepository.findById(email).get().getCode(), "12345678");
    }

    @Test
    @DisplayName("실제 이메일로 전달 받은 인증번호 확인")
    public void verifyCode()
    {
        // given
        String email = "gyeongnamdev@gmail.com";
        String code = "49782012";

        // when
        Optional<Email> optional = emailRepository.findById(email);

        // then
        Assertions.assertTrue(emailRepository.existsById(optional.get().getEmail()));
        Assertions.assertFalse(emailRepository.existsById("gyeongnam@gmail.com"));
        Assertions.assertEquals(emailRepository.findById(email).get().getCode(), code);
        Assertions.assertNotEquals(emailRepository.findById(email).get().getCode(), "12345678");
    }
}