package com.techit.withus.web.users.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Roles
{
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    EXPIRED_MEMBER("ROLE_EXPIRED_USER");

    private final String prefixedName;
}
