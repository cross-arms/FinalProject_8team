package com.techit.withus.web.users.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Roles
{
    ROLE_ADMIN("ADMIN"),                     // 관리자
    ROLE_USER("USER"),                       // 가입 및 이메일 인증을 마친 유저
    ROLE_EXPIRED_USER("EXPIRED_USER");       // 가입 및 이메일 인증을 했지만 관리자가 권한을 박탈한 유저

    private final String name;
}
