package com.techit.withus.oauth;

import com.techit.withus.jwt.JwtService;
import com.techit.withus.security.SecurityUser;
import com.techit.withus.web.users.domain.entity.Users;
import com.techit.withus.web.users.domain.enumeration.Roles;
import com.techit.withus.web.users.domain.mapper.UserMapper;
import com.techit.withus.web.users.repository.UserRepository;
import io.netty.util.internal.ThreadLocalRandom;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.random.RandomGenerator;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler
{
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        // 이미 가입 되어 있는 회원 조회, 최초 로그인이면 가입한 뒤 조회.
        Users userEntity = userRepository
                .findByEmail(email)
                .orElse(this.SignUp(oAuth2User));

        SecurityUser securityUser = UserMapper.INSTANCE.toSecurityUser(userEntity);
        String accessToken = jwtService.createAccessToken(securityUser);
        String refreshToken = jwtService.createRefreshToken(securityUser);

        log.info("accessToken: {}", accessToken);
        log.info("refreshToken: {}", refreshToken);

        // 타겟 설정은 React 이후
    }

    private String getRandomUsername(String provider)
    {
        RandomGenerator randomGenerator = ThreadLocalRandom.current();
        return provider + randomGenerator.nextLong(1000000000L, 9999999999L);
    }

    private Users SignUp(OAuth2User oAuth2User)
    {
        String email = oAuth2User.getAttribute("email");
        String provider = oAuth2User.getAttribute("provider");
        String username = this.getRandomUsername(provider);
        String role = Roles.ROLE_USER.name();
        Users userEntity = UserMapper.INSTANCE.toUsers(email, provider, username, role);
        return userRepository.save(userEntity);
    }
}
