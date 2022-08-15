package com.practice.security.domain.member;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_MEMBER;

    @Override
    public String getAuthority() {
        return name();
    }
}
