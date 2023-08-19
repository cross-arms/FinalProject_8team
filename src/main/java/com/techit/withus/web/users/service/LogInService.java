package com.techit.withus.web.users.service;

import com.techit.withus.jwt.JwtService;
import com.techit.withus.security.SecurityService;
import com.techit.withus.security.SecurityUser;
import com.techit.withus.web.common.domain.dto.TokenDTO;
import com.techit.withus.web.users.domain.dto.LogInDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogInService
{
    private final JwtService jwtService;
    private final SecurityService securityService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인하는 메서드이다.
     * 먼저 로그인 요청으로 전달 받은 DTO의 username으로 조회하고, 비밀번호가 일치하면
     * AccessToken과 RefreshToken을 만들어서 전달한다.
     */
    public TokenDTO login(LogInDTO logInDTO)
    {
        SecurityUser securityUser = securityService.loadUserByUsername(logInDTO.getUsername());

        this.matchPasswords(logInDTO.getPassword(), securityUser.getPassword());

        return TokenDTO
                .builder()
                .accessToken(jwtService.createAccessToken(securityUser))
                .refreshToken(jwtService.createRefreshToken(securityUser))
                .build();
    }

    /**
     * 비밀번호를 인코딩하는 메서드이다.
     * 유저에게 전달 받은 비밀번호를 SecurityConfig 클래스에서 Bean으로 등록한
     * BCryptPasswordEncoder를 이용해 인코딩한다.
     */
    private String encodePassword(String password)
    {
        return passwordEncoder.encode(password);
    }

    /**
     * 입력 받은 비밀번호와 실제 인코딩 되어 저장된 DB의 비밀번호를 비교하는 메서드
     */
    private void matchPasswords(String password, String encodedPassword)
    {
        if (!passwordEncoder.matches(password, encodedPassword))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
