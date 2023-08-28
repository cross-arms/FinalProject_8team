package com.techit.withus.security;

import com.techit.withus.jwt.JwtConfig;
import jakarta.servlet.SessionTrackingMode;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig
{
    private final JwtConfig jwtConfig;

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    private static final String[] PERMIT_ALL_PATTERNS
            = new String[]
            {
                    "v3/api-docs/**",
                    "swagger-ui/**"
            };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   HandlerMappingIntrospector handlerMappingIntrospector) throws Exception
    {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtConfig, AuthorizationFilter.class)
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(PathRequest.toH2Console())
                                .permitAll()
                                .requestMatchers(
                                        Stream
                                                .of(PERMIT_ALL_PATTERNS)
                                                .map(AntPathRequestMatcher::antMatcher)
                                                .toArray(AntPathRequestMatcher[]::new))
                                .permitAll()
                                .anyRequest()
                                .authenticated());
        return http.build();
    }
}
