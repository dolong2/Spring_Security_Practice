package com.practice.security.service;

import com.practice.security.dto.request.MemberReqDto;
import com.practice.security.dto.request.SignInDto;
import com.practice.security.dto.response.MemberResDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface MemberService {
    Long join(MemberReqDto memberReqDto);

    Map<String, Object> login(SignInDto signInDto);

    void logout();

    MemberResDto getMemberByIdx(Long memberIdx);

    List<MemberResDto> getAllMember();

    void withdrawal();

    MemberResDto findMe();
}
