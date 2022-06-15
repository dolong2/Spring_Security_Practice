package com.practice.security.service.impl;

import com.practice.security.configuration.security.jwt.TokenProvider;
import com.practice.security.domain.Member;
import com.practice.security.dto.request.MemberReqDto;
import com.practice.security.dto.request.SignInDto;
import com.practice.security.dto.response.MemberResDto;
import com.practice.security.repository.MemberRepository;
import com.practice.security.service.MemberService;
import com.practice.security.util.CurrentMemberUtil;
import com.practice.security.util.ResponseDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final CurrentMemberUtil currentMemberUtil;

    @Override
    @Transactional
    public Long join(MemberReqDto memberDto){
        if(!memberRepository.findOneByEmail(memberDto.getEmail()).isEmpty()){
            throw new RuntimeException();
        }
        Member member = memberDto.toEntity(passwordEncoder.encode(memberDto.getPassword()));
        System.out.println("member.getPassword() = " + member.getPassword());
        return memberRepository.save(member).getId();
    }

    @Override
    @Transactional
    public Map<String, Object> login(SignInDto signInDto){
        Optional<Member> byEmail = memberRepository.findOneByEmail(signInDto.getEmail());
        if(byEmail.isEmpty()){
            throw new RuntimeException("Can't find member by email");
        }
        Member member = byEmail.get();
        if(!passwordEncoder.matches(signInDto.getPassword(), member.getPassword())){
            throw new RuntimeException("Password Not Matches");
        }
        String accessToken = tokenProvider.generateAccessToken(member.getEmail());
        String refreshToken = tokenProvider.generateRefreshToken(member.getEmail());
        Map<String, Object> loginResponse = getLoginResponse(member, accessToken, refreshToken);
        return loginResponse;
    }

    @Override
    @Transactional
    public void logout() {
        Member member = currentMemberUtil.getCurrentMember();
        member.updateRefreshToken(null);
    }

    @Override
    public MemberResDto getMemberByIdx(Long memberIdx) {
        Member member = memberRepository.findById(memberIdx)
                .orElseThrow(() -> new RuntimeException());
        return ResponseDtoUtil.mapping(member, MemberResDto.class);
    }

    private Map<String, Object> getLoginResponse(Member member, String accessToken, String refreshToken) {
        Map<String, Object> login = new HashMap<>();
        login.put("member_id", member.getId());
        login.put("accessToken", accessToken);
        login.put("refreshToken", refreshToken);
        return login;
    }
}
