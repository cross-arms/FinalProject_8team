package com.techit.withus.web.users.service;

import com.techit.withus.common.exception.EntityNotFoundException;
import com.techit.withus.security.SecurityUser;
import com.techit.withus.web.users.domain.dto.EmailDTO;
import com.techit.withus.web.users.domain.dto.StatisticsDTO;
import com.techit.withus.web.users.domain.dto.UserDto;
import com.techit.withus.web.users.domain.dto.UserDto.UserResponse;
import com.techit.withus.web.users.domain.dto.UserInfoDTO;
import com.techit.withus.web.users.domain.entity.Users;
import com.techit.withus.web.users.domain.enumeration.Roles;
import com.techit.withus.web.users.domain.mapper.UserMapper;
import com.techit.withus.web.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.techit.withus.common.exception.ErrorCode.MEMBER_NOT_EXIST;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;

    public UserResponse getUserInfoBy(Long userId) {
        return UserResponse.create(findUser(userId));
    }

    public Users findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(MEMBER_NOT_EXIST, userId)
        );
    }

    public UserInfoDTO getUserInfo(Long userId)
    {
        Users userEntity = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_EXIST, userId));

        log.info(userEntity.toString());
        return UserMapper.INSTANCE.toUserInfoDTO(userEntity);
    }
}
