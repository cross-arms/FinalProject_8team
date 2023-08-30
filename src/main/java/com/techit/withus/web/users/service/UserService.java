package com.techit.withus.web.users.service;

import com.techit.withus.common.exception.EntityNotFoundException;
import com.techit.withus.security.SecurityUser;
import com.techit.withus.web.users.domain.dto.EmailDTO;
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

    public void updateEmailAndRole(SecurityUser user, EmailDTO emailDTO)
    {
        log.info("UserService.updateEmailAndRole START");
        // 업데이트할 유저를 가져오기
        Users userEntity = userRepository
                .findById(user.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        // 이메일 인증을 완료하면 ROLE_USER
        String role = Roles.ROLE_USER.name();
        String email = emailDTO.getAddress();

        Users updatedUserEntity
                = UserMapper.INSTANCE.updateEmailAndRole(userEntity, role, email);

        userRepository.save(updatedUserEntity);
    }

    public Users getUserInfo(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(MEMBER_NOT_EXIST, userId)
        );
    }
}
