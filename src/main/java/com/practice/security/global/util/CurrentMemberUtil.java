package com.practice.security.global.util;

import com.practice.security.global.configuration.security.auth.AuthDetails;
import com.practice.security.domain.member.Member;
import com.practice.security.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentMemberUtil {

    private final MemberRepository memberRepository;

    public static String getCurrentEmail(){
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            email = ((AuthDetails) principal).getEmail();
        } else {
            email = principal.toString();
        }
        return email;
    }

    public Member getCurrentMember(){
        String currentEmail = getCurrentEmail();
        return memberRepository.findOneByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException());
    }
}
