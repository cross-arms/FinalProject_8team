package com.techit.withus.jwt;
import com.techit.withus.common.exception.AuthenticationException;
import com.techit.withus.common.exception.ErrorCode;
import com.techit.withus.redis.hashes.RefreshToken;
import com.techit.withus.redis.repository.RefreshTokenRepository;
import com.techit.withus.security.SecurityService;
import com.techit.withus.security.SecurityUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class JwtService
{
    private final Key key;
    private final JwtParser parser;
    private final SecurityService securityService;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtService(@Value("${jwt.secret}") String key,
                      SecurityService securityService,
                      RefreshTokenRepository refreshTokenRepository)
    {
        this.key = Keys.hmacShaKeyFor(key.getBytes());
        this.parser = Jwts.parserBuilder().setSigningKey(this.key).build();
        this.securityService = securityService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String getSubject(String token)
    {
        return parser.parseClaimsJws(token).getBody().getSubject();
    }

    // Access Token 생성한다.
    public String createAccessToken(SecurityUser securityUser)
    {
        Claims claims = Jwts
                .claims()
                .setSubject(securityUser.getEmail())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(60 * 60))); // 60초 1시간
        claims.put("uid", securityUser.getUserId());
        claims.put("auth", securityUser.getAuthorities());

        return Jwts
                .builder()
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Refresh Token 생성한다.
    public String createRefreshToken(SecurityUser securityUser)
    {
        Claims claims = Jwts
                .claims()
                .setSubject(securityUser.getEmail())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(60 * 60 * 24)));

        String refreshToken = Jwts
                .builder()
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // RefreshToken을 만들어서, Redis에 저장한다.
        refreshTokenRepository.save(
                new RefreshToken(securityUser.getEmail(), refreshToken)
        );

        return refreshToken;
    }

    // 요청의 헤더에 토큰이 있는지 확인하고, 있으면 토큰의 값만 반환한다.
    public String getAccessToken(HttpServletRequest request)
    {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorization != null && authorization.startsWith("Bearer "))
            return authorization.split(" ")[1];

        return null;
    }

    // 쿠키를 생성한다.
    public Cookie createCookie(SecurityUser securityUser)
    {
        String cookieName = "refreshToken";
        String cookieValue = this.createRefreshToken(securityUser);
        Cookie cookie = new Cookie(
                cookieName,
                URLEncoder.encode(cookieValue, StandardCharsets.UTF_8));
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24); // 60초 1시간 하루
        return cookie;
    }

    // 토큰을 검증한다.
    public boolean validateToken(String token)
    {
        try {
            parser.parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            log.info("Token Error");
        } catch (ExpiredJwtException e) {
            log.info("Token Expired");
        }
        return false;
    }

    public Cookie findCookie(HttpServletRequest request, String cookieName)
    {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName()))
                return cookie;
        }
        throw new AuthenticationException(ErrorCode.COOKIE_MISSING);
    }
}
