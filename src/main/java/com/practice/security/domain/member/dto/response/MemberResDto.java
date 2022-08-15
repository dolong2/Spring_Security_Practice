package com.practice.security.domain.member.dto.response;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResDto {
    private Long id;
    private String name;
}
