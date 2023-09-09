package com.techit.withus.jwt;

import com.techit.withus.common.exception.AuthenticationException;
import com.techit.withus.common.exception.ErrorCode;
import com.techit.withus.redis.service.RefreshTokenService;
import com.techit.withus.security.SecurityService;
import com.techit.withus.security.SecurityUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JwtConfig extends OncePerRequestFilter
{
    private final SecurityService securityService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    /**
     * 1. 요청의 헤더를 분석한다.
     *     -> Authorization 헤더인지, 맞다면 Bearer 스키마를 포함하는 어세스 토큰을 제시하는지
     * 2. 어세스 토큰을 분석한다.
     *     -> 어세스 토큰을 파싱하여 문제가 없는지 확인한다.
     * 3. 리프레시 토큰을 분석한다.
     *     -> 1) 어세스 토큰으로부터 유저의 정보를 얻는다.
     *     -> 2) 유저의 정보를 키 값으로, 리프레시 토큰을 밸류 값으로 가지는 DB를 통해 리프레시 토큰을 얻는다.
     *     -> 3) 해당 리프레시 토큰과 쿠키로부터 얻은 리프레시 토큰이 일치하는지 확인한다.
     * 4. SpringSecurity가 다음 역할을 수행할 수 있도록 데이터를 넘겨준다.
     *     -> 유저의 정보를 Authentication > SecurityContext > SecurityContextHolder 순서대로 담는다.
     */

    /*
    // Token 인증이 필요없는 URL을 따로 지정, 일단 모두 토큰이 필요없는 상태로 지정
    private static final List<String> EXCLUDE_URL
            = List.of("auth/**", "resources/**");

    // Token
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return EXCLUDE_URL
                .stream()
                .anyMatch(
                        exclude -> exclude
                                .equalsIgnoreCase(
                                        request.getServletPath()));
    }
     */

    private String getRefreshTokenInCookie(HttpServletRequest request)
    {
        Cookie[] cookies = request.getCookies();

        if (cookies != null)  {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName()))
                    return cookie.getValue();
            }
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        // 1. 요청의 헤더를 분석한다.
        String token = jwtService.getAccessToken(request);

        // 2. 어세스 토큰을 분석한다.
        if (token != null && jwtService.validateToken(token)) {

            // 3. 리프레시 토큰을 분석한다.
            // 3-1) 어세스 토큰으로부터 얻은 유저의 정보
            String subject = jwtService.getSubject(token);
            // 3-2) 해당 유저의 정보로 DB에서 조회한 리프레시 토큰
            String refreshTokenInRedis = refreshTokenService.findRefreshToken(subject);
            // 3-3) 쿠키로부터 얻은 리프레시 토큰
            String refreshTokenInCookie = this.getRefreshTokenInCookie(request);

            if (refreshTokenInRedis.equals(refreshTokenInCookie)) {
                this.setSecurityContextHolder(refreshTokenInRedis);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void setSecurityContextHolder(String token)
    {
        String email = jwtService.getSubject(token);
        SecurityUser securityUser = securityService.loadUserByUsername(email);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        AbstractAuthenticationToken abstractAuthenticationToken
                = new UsernamePasswordAuthenticationToken(securityUser, token, securityUser.getAuthorities());
        context.setAuthentication(abstractAuthenticationToken);
        SecurityContextHolder.setContext(context);
    }
}
