package com.practice.security.domain.member.service.impl;

import com.practice.security.global.configuration.security.jwt.TokenProvider;
import com.practice.security.domain.member.Member;
import com.practice.security.domain.member.dto.request.MemberReqDto;
import com.practice.security.domain.member.dto.request.SignInDto;
import com.practice.security.domain.member.dto.response.MemberResDto;
import com.practice.security.global.exception.ErrorCode;
import com.practice.security.global.exception.errors.DuplicateMemberException;
import com.practice.security.global.exception.errors.MemberNotFindException;
import com.practice.security.global.exception.errors.PasswordNotCorrectException;
import com.practice.security.domain.member.repository.MemberRepository;
import com.practice.security.domain.member.service.MemberService;
import com.practice.security.global.util.CurrentMemberUtil;
import com.practice.security.global.util.ResponseDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
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
    @Transactional(rollbackFor = Exception.class)
    public Long join(MemberReqDto memberDto){
        if(!memberRepository.findOneByEmail(memberDto.getEmail()).isEmpty()){
            throw new DuplicateMemberException("Member already exists", ErrorCode.DUPLICATE_MEMBER);
        }
        Member member = memberDto.toEntity(passwordEncoder.encode(memberDto.getPassword()));
        return memberRepository.save(member).getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> login(SignInDto signInDto){
        Optional<Member> byEmail = memberRepository.findOneByEmail(signInDto.getEmail());
        if(byEmail.isEmpty()){
            throw new MemberNotFindException("Can't find member by email", ErrorCode.MEMBER_NOT_FIND);
        }
        Member member = byEmail.get();
        if(!passwordEncoder.matches(signInDto.getPassword(), member.getPassword())){
            throw new PasswordNotCorrectException("Password Not Matches", ErrorCode.PASSWORD_NOT_CORRECT);
        }
        String accessToken = tokenProvider.generateAccessToken(member.getEmail());
        String refreshToken = tokenProvider.generateRefreshToken(member.getEmail());
        member.updateRefreshToken(refreshToken);
        Map<String, Object> loginResponse = getLoginResponse(member, accessToken, refreshToken);
        return loginResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void logout() {
        Member member = currentMemberUtil.getCurrentMember();
        member.updateRefreshToken(null);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public MemberResDto getMemberByIdx(Long memberIdx) {
        Member member = memberRepository.findById(memberIdx)
                .orElseThrow(() -> new MemberNotFindException("Can't find member by email", ErrorCode.MEMBER_NOT_FIND));
        return ResponseDtoUtil.mapping(member, MemberResDto.class);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<MemberResDto> getAllMember() {
        List<Member> all = memberRepository.findAll();
        return ResponseDtoUtil.mapAll(all, MemberResDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void withdrawal() {
        Member member = currentMemberUtil.getCurrentMember();
        logout();
        memberRepository.delete(member);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public MemberResDto findMe() {
        Member member = currentMemberUtil.getCurrentMember();
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
