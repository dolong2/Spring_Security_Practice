package com.practice.security.dto.request;

import com.practice.security.domain.Member;
import com.practice.security.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;

@Getter
@AllArgsConstructor
public class MemberReqDto {
    private String email;
    private String password;
    private String name;

    public Member toEntity(String password){
        return Member.builder()
                .email(this.email)
                .name(this.name)
                .password(password)
                .roles(Collections.singletonList(Role.ROLE_MEMBER))
                .build();
    }
}
