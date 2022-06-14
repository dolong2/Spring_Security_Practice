package com.practice.security.service;

import com.practice.security.configuration.security.jwt.TokenProvider;
import com.practice.security.domain.Member;
import com.practice.security.dto.request.MemberReqDto;
import com.practice.security.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(MemberReqDto memberDto){
        if(!memberRepository.findOneByEmail(memberDto.getEmail()).isEmpty()){
            throw new RuntimeException();
        }
        Member member = memberDto.toEntity(passwordEncoder.encode(memberDto.getPassword()));
        System.out.println("member.getPassword() = " + member.getPassword());
        return memberRepository.save(member).getId();
    }
}
