package com.techit.withus.security;

import com.techit.withus.web.users.domain.entity.Users;
import com.techit.withus.web.users.domain.mapper.UserMapper;
import com.techit.withus.web.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService
{
    private final UserRepository userRepository;

    /**
     * Entity는 Service 레이어에서 무조건 데이터베이스와 소통한다.
     * Entity의 데이터는 Controller로 보낼 땐 다시 DTO로 만들어 보낸다.
     */
    @Override
    public SecurityUser loadUserByUsername(String email) throws UsernameNotFoundException {

        Users userEntity = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return UserMapper.INSTANCE.toSecurityUser(userEntity);
    }
}
