package com.practice.security.controller;

import com.practice.security.dto.request.MemberReqDto;
import com.practice.security.dto.request.SignInDto;
import com.practice.security.dto.response.MemberResDto;
import com.practice.security.service.MemberService;
import com.practice.security.util.response.ResponseService;
import com.practice.security.util.response.result.CommonResultResponse;
import com.practice.security.util.response.result.SingleResultResponse;
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
