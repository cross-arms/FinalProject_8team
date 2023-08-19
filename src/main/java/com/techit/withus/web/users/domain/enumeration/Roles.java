package com.techit.withus.web.users.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Roles
{
    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER"),
    ROLE_EXPIRED_MEMBER("EXPIRED_USER");

    private final String name;
}
