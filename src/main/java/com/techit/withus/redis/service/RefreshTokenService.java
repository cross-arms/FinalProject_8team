package com.techit.withus.redis.service;

import com.techit.withus.common.exception.AuthenticationException;
import com.techit.withus.common.exception.ErrorCode;
import com.techit.withus.redis.hashes.RefreshToken;
import com.techit.withus.redis.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService
{
    private final RefreshTokenRepository refreshTokenRepository;

    public void deleteRefreshToken(String email)
    {
        refreshTokenRepository.deleteById(email);
    }

    public String findRefreshToken(String email)
    {
        RefreshToken refreshToken = refreshTokenRepository
                .findById(email)
                .orElseThrow(() -> new AuthenticationException(ErrorCode.TOKEN_NOT_FOUND));

        return refreshToken.getRefreshToken();
    }
}
