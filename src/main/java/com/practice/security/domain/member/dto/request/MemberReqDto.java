package com.practice.security.domain.member.dto.request;

import com.practice.security.domain.member.Member;
import com.practice.security.domain.member.Role;
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
