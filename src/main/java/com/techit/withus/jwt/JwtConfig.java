package com.techit.withus.jwt;

import com.techit.withus.security.SecurityService;
import com.techit.withus.security.SecurityUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JwtConfig extends OncePerRequestFilter
{
    private final SecurityService securityService;
    private final JwtService jwtService;

    private static final List<String> EXCLUDE_URL =
            Collections.unmodifiableList(
                    Arrays.asList(
                            "/**"
                    ) // Token 인증이 필요없는 URL을 따로 지정, 일단 모두 토큰이 필요없는 상태로 지정
            );
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return EXCLUDE_URL
                .stream()
                .anyMatch(
                        exclude -> exclude
                                .equalsIgnoreCase(
                                        request.getServletPath()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.split(" ")[1];

            if (jwtService.isValid(token)) {
                String username = jwtService.getUsername(token);
                SecurityUser securityUser = securityService.loadUserByUsername(username);

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                AbstractAuthenticationToken abstractAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(securityUser, token, securityUser.getAuthorities());
                context.setAuthentication(abstractAuthenticationToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request, response);
    }
}
