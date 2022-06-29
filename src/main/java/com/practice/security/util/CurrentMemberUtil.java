package com.practice.security.util;

import com.practice.security.configuration.security.auth.AuthDetails;
import com.practice.security.domain.Member;
import com.practice.security.repository.MemberRepository;
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
