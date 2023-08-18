package com.techit.withus.jwt;
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

    public JwtService(@Value("${jwt.secret}") String key)
    {
        this.key = Keys.hmacShaKeyFor(key.getBytes());
        this.parser = Jwts.parserBuilder().setSigningKey(this.key).build();
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
     * 토큰을 만드는 메서드
     */
    public String createAccessToken(SecurityUser securityUser)
    {
        Claims claims = Jwts
                .claims()
                .setSubject(securityUser.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(1800)));
        claims.put("uid", securityUser.getUserId());
        claims.put("auth", securityUser.getAuthorities());

        return Jwts
                .builder()
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
