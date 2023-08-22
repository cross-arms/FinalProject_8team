package com.techit.withus.web.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService
{
    private final PasswordEncoder passwordEncoder;


    /**
     * 비밀번호를 인코딩하는 메서드이다.
     * 유저에게 전달 받은 비밀번호를 SecurityConfig 클래스에서 Bean으로 등록한
     * BCryptPasswordEncoder를 이용해 인코딩한다.
     */
    private String encodePassword(String password)
    {
        return passwordEncoder.encode(password);
    }

}
