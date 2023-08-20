package com.techit.withus.security;

import com.techit.withus.jwt.JwtService;
import com.techit.withus.web.common.domain.dto.TokenDTO;
import com.techit.withus.web.users.domain.dto.LogInDTO;
import com.techit.withus.web.users.domain.entity.Users;
import com.techit.withus.web.users.domain.mapper.UserMapper;
import com.techit.withus.web.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService
{
    private final UserRepository userRepository;

    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> entity = userRepository.findByUsername(username);
        if (entity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else
            return UserMapper.INSTANCE.toSecurityUser(entity.get());
    }
}
