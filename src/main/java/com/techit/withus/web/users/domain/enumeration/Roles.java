package com.techit.withus.web.users.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Roles
{
    // 관리자
    ROLE_ADMIN("ADMIN"),
    // 가입은 했지만 이메일 인증을 하지 않은 유저
    ROLE_INVALIDATE_USER("INVALIDATE_USER"),
    // 가입 및 이메일 인증을 마친 유저
    ROLE_USER("USER"),
    // 가입 및 이메일 인증을 했지만 관리자가 권한을 박탈한 유저
    ROLE_EXPIRED_USER("EXPIRED_USER");

    private final String name;
}
