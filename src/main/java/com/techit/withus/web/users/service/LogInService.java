package com.techit.withus.web.users.service;

import com.techit.withus.jwt.JwtService;
import com.techit.withus.security.SecurityService;
import com.techit.withus.security.SecurityUser;
import com.techit.withus.jwt.JwtDTO;
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

    // 로그인
    public JwtDTO logIn(LogInDTO logInDTO)
    {
        SecurityUser securityUser
                = securityService.loadUserByUsername(logInDTO.getEmail());

        this.matchPasswords(logInDTO.getPassword(), securityUser.getPassword());

        return JwtDTO
                .builder()
                .accessToken(jwtService.createAccessToken(securityUser))
                .refreshToken(jwtService.createRefreshToken(securityUser))
                .build();
    }

    // 입력 받은 비밀번호와 실제 인코딩 되어 저장된 DB의 비밀번호를 비교하는 메서드
    private void matchPasswords(String password, String encodedPassword)
    {
        if (!passwordEncoder.matches(password, encodedPassword))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
