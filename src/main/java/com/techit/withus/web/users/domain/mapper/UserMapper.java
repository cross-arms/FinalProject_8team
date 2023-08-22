package com.techit.withus.web.users.domain.mapper;

import com.techit.withus.security.SecurityUser;
import com.techit.withus.web.users.domain.dto.SignUpDTO;
import com.techit.withus.web.users.domain.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper
{
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    SecurityUser toSecurityUser(Users users);

    @Mapping(target = "password", source = "encodedPassword")
    Users toUsers(SignUpDTO signUpDTO,
                  String encodedPassword,
                  String role);

    @Mapping(target = "role", source = "role")
    @Mapping(target = "email", source = "email")
    Users updateEmailAndRole(Users userEntity,
                             String role,
                             String email);
}
