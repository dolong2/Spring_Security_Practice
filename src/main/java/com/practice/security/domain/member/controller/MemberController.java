package com.practice.security.domain.member.controller;

import com.practice.security.domain.member.dto.request.MemberReqDto;
import com.practice.security.domain.member.dto.request.SignInDto;
import com.practice.security.domain.member.dto.response.MemberResDto;
import com.practice.security.domain.member.service.MemberService;
import com.practice.security.global.util.response.ResponseService;
import com.practice.security.global.util.response.result.CommonResultResponse;
import com.practice.security.global.util.response.result.SingleResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/member")
public class MemberController {
    private final MemberService memberService;
    private final ResponseService responseService;

    @PostMapping("/join")
    public CommonResultResponse join(@RequestBody MemberReqDto memberReqDto){
        memberService.join(memberReqDto);
        return responseService.getSuccessResult();
    }

    @PostMapping("/login")
    public SingleResultResponse login(@RequestBody SignInDto signInDto){
        return responseService.getSingleResult(memberService.login(signInDto));
    }

    @PostMapping("/logout")
    public CommonResultResponse logout(){
        memberService.logout();
        return responseService.getSuccessResult();
    }

    @DeleteMapping
    public CommonResultResponse withdrawal(){
        memberService.withdrawal();
        return responseService.getSuccessResult();
    }

    @GetMapping("/me")
    public SingleResultResponse<MemberResDto> findMe(){
        return responseService.getSingleResult(memberService.findMe());
    }
}
