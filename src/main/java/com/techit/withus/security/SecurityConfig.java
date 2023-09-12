package com.techit.withus.security;

import com.techit.withus.jwt.JwtConfig;
import com.techit.withus.oauth.OAuth2FailureHandler;
import com.techit.withus.oauth.OAuth2Service;
import com.techit.withus.oauth.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig
{
    private final JwtConfig jwtConfig;
    private final CorsConfig corsConfig;
    private final OAuth2Service oAuth2Service;
    private final OAuth2SuccessHandler successHandler;
    private final OAuth2FailureHandler failureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   HandlerMappingIntrospector introspector) throws Exception
    {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(corsConfig.corsFilter())
                .addFilterBefore(jwtConfig, AuthorizationFilter.class)
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(PathRequest.toH2Console())
                                .permitAll()
                                .requestMatchers(
                                        new MvcRequestMatcher(
                                                introspector,
                                                "/**"))
                                .permitAll()
                                .anyRequest()
                                .authenticated())
        // OAuth2.0 설정
                .oauth2Login(
                        oauth2Login -> oauth2Login
                                .loginPage("/oauth")
                                .userInfoEndpoint(
                                        userInfo -> userInfo
                                                .userService(oAuth2Service)
                                )
                                .successHandler(successHandler)
                                .failureHandler(failureHandler));

        return http.build();
    }
}
