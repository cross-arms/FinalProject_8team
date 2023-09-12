package com.techit.withus.web.users.service;

import com.techit.withus.common.exception.AuthenticationException;
import com.techit.withus.common.exception.EntityNotFoundException;
import com.techit.withus.common.exception.ErrorCode;
import com.techit.withus.jwt.JwtService;
import com.techit.withus.oauth.OAuth2Provider;
import com.techit.withus.redis.service.BlackListService;
import com.techit.withus.redis.service.RefreshTokenService;
import com.techit.withus.security.SecurityService;
import com.techit.withus.security.SecurityUser;
import com.techit.withus.web.feeds.domain.entity.feed.Images;
import com.techit.withus.web.users.domain.dto.*;
import com.techit.withus.web.users.domain.entity.Users;
import com.techit.withus.web.users.domain.enumeration.Roles;
import com.techit.withus.web.users.domain.mapper.UserMapper;
import com.techit.withus.web.users.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService
{
    private final JwtService jwtService;
    private final SecurityService securityService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final BlackListService blackListService;
    private final UserRepository userRepository;
    private final StatisticsService statisticsService;

    // 회원가입
    @Transactional
    public void signUp(SignUpDTO signUpDTO)
    {
        // 먼저 email이 중복되었는지 확인
        this.checkDuplicateUser(signUpDTO.getEmail());
        // 비밀번호 인코딩
        String encodedPassword = this.encodePassword(signUpDTO.getPassword());
        String role = Roles.ROLE_USER.name();
        String provider = OAuth2Provider.LOCAL.name();
        Users userEntity = UserMapper.INSTANCE.toUsers(signUpDTO, encodedPassword, role, provider);
        userRepository.save(userEntity);
    }

    // 로그인
    @Transactional(readOnly = true)
    public void logIn(LogInDTO logInDTO, HttpServletResponse response)
    {
        SecurityUser securityUser
                = securityService.loadUserByUsername(logInDTO.getEmail());

        this.matchPasswords(logInDTO.getPassword(), securityUser.getPassword());

        Cookie cookie = jwtService.createCookie(securityUser);
        String accessToken = jwtService.createAccessToken(securityUser);

        response.addCookie(cookie);
        response.addHeader(HttpHeaders.AUTHORIZATION, accessToken);
    }

    // 로그아웃
    @Transactional(readOnly = true)
    public void logOut(HttpServletRequest request)
    {
        String accessToken = jwtService.getAccessToken(request);
        String email = jwtService.getSubject(accessToken);
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("refreshToken".equals(cookie.getName())) {
                refreshTokenService.deleteRefreshToken(email);
                blackListService.addBlackList(accessToken);
            } else throw new AuthenticationException(ErrorCode.COOKIE_MISSING);
        }
    }

    // 입력 받은 비밀번호와 실제 인코딩 되어 저장된 DB의 비밀번호를 비교하는 메서드
    private void matchPasswords(String password, String encodedPassword)
    {
        if (!passwordEncoder.matches(password, encodedPassword))
            throw new AuthenticationException(ErrorCode.AUTHENTICATION_FAILED);
    }

    // 입력 받은 username이 중복됐는지 확인한다.
    private void checkDuplicateUser(String email)
    {
        if (userRepository.existsByEmail(email))
            throw new AuthenticationException(ErrorCode.MEMBER_ALREADY_EXIST);
    }

    // 비밀번호를 인코딩 한다.
    private String encodePassword(String password)
    {
        return passwordEncoder.encode(password);
    }

    // Username의 중복을 검사한다.
    public void checkDuplicateUsername(String username)
    {
        if (userRepository.existsByUsername(username))
            throw new AuthenticationException(ErrorCode.MEMBER_ALREADY_EXIST);
    }

    public void checkPassword(SecurityUser securityUser,
                              String password)
    {
        this.matchPasswords(password, securityUser.getPassword());
    }

    public void editUser(SecurityUser securityUser,
                         EditDTO editDTO)
    {
        this.matchPasswords(editDTO.getBeforePassword(), securityUser.getPassword());
        String encodedPassword = this.encodePassword(editDTO.getAfterPassword());
        Users userEntity = userRepository
                .findById(securityUser.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.AUTHORIZATION_FAILED));

        Users newUserEntity = UserMapper.INSTANCE.toUsers(userEntity, encodedPassword, editDTO);
        userRepository.save(newUserEntity);
    }

}
