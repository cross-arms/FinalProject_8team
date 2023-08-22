package com.techit.withus.jwt;
import com.techit.withus.redis.hashes.RefreshToken;
import com.techit.withus.redis.repository.RefreshTokenRepository;
import com.techit.withus.security.SecurityUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService
{
    private final Key key;
    private final JwtParser parser;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtService(@Value("${jwt.secret}") String key,
                      RefreshTokenRepository refreshTokenRepository)
    {
        this.key = Keys.hmacShaKeyFor(key.getBytes());
        this.parser = Jwts.parserBuilder().setSigningKey(this.key).build();
        this.refreshTokenRepository = refreshTokenRepository;
    }

    /**
     * 토큰의 payload의 claims에 설정한 만료시간(.setExpiration())이나,
     * signature(.signWith())의 무결성을 검증하는 메서드
     */
    public boolean isValid(String token)
    {
        try {
            parser.parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    public String getUsername(String token)
    {
        return parser.parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Access Token 생성한다.
     */
    public String createAccessToken(SecurityUser securityUser)
    {
        Claims claims = Jwts
                .claims()
                .setSubject(securityUser.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(60 * 60)));
        claims.put("uid", securityUser.getUserId());
        claims.put("auth", securityUser.getAuthorities());

        return Jwts
                .builder()
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Refresh Token 생성한다.
     */
    public String createRefreshToken(SecurityUser securityUser)
    {
        Claims claims = Jwts
                .claims()
                .setSubject(securityUser.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(60 * 60 * 24)));

        String refreshToken = Jwts
                .builder()
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // RefreshToken을 만들어서, Redis에 저장한다.
        refreshTokenRepository.save(
                new RefreshToken(refreshToken, securityUser.getUserId())
        );

        return refreshToken;
    }
}
