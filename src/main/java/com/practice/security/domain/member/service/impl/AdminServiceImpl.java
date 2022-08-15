package com.practice.security.domain.member.service.impl;

import com.practice.security.domain.member.Member;
import com.practice.security.domain.member.Role;
import com.practice.security.domain.member.service.AdminService;
import com.practice.security.global.exception.ErrorCode;
import com.practice.security.global.exception.errors.MemberNotFindException;
import com.practice.security.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void grantRole(Long memberIdx) {
        Member member = memberRepository.findById(memberIdx)
                .orElseThrow(() -> new MemberNotFindException("Can't find member by email", ErrorCode.MEMBER_NOT_FIND));
        member.getRoles().add(Role.ROLE_ADMIN);
    }
}
