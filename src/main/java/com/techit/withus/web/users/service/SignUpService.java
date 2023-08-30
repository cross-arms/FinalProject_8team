package com.techit.withus.web.users.service;

import com.techit.withus.oauth.OAuth2Provider;
import com.techit.withus.web.users.domain.dto.SignUpDTO;
import com.techit.withus.web.users.domain.entity.Users;
import com.techit.withus.web.users.domain.enumeration.Roles;
import com.techit.withus.web.users.domain.mapper.UserMapper;
import com.techit.withus.web.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SignUpService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public void signUp(SignUpDTO signUpDTO)
    {
        // 먼저 username이 중복되었는지 확인
        this.checkDuplicateUser(signUpDTO.getEmail());
        // 비밀번호 인코딩
        String encodedPassword = this.encodePassword(signUpDTO.getPassword());
        String role = Roles.ROLE_USER.name();
        String provider = OAuth2Provider.LOCAL.name();
        Users userEntity = UserMapper.INSTANCE.toUsers(signUpDTO, encodedPassword, role, provider);
        userRepository.save(userEntity);
    }

    // 입력 받은 username이 중복됐는지 확인한다.
    private void checkDuplicateUser(String email)
    {
        if (userRepository.existsByEmail(email))
            throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

    // 비밀번호를 인코딩 한다.
    private String encodePassword(String password)
    {
        return passwordEncoder.encode(password);
    }
}
