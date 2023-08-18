package com.techit.withus.web.users.domain.mapper;

import com.techit.withus.security.SecurityUser;
import com.techit.withus.web.users.domain.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper
{
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    SecurityUser toSecurityUser(Users users);
}
