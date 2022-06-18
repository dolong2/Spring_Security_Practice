package com.practice.security.controller;

import com.practice.security.dto.response.MemberResDto;
import com.practice.security.service.MemberService;
import com.practice.security.util.response.ResponseService;
import com.practice.security.util.response.result.ListResultResponse;
import com.practice.security.util.response.result.SingleResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final MemberService memberService;
    private final ResponseService responseService;

    @GetMapping("/memberInfo/{memberIdx}")
    public SingleResultResponse<MemberResDto> getOneMember(@PathVariable Long memberIdx){
        return responseService.getSingleResult(memberService.getMemberByIdx(memberIdx));
    }

    @GetMapping("/memberInfo")
    public ListResultResponse<MemberResDto> getAllMember(){
        return responseService.getListResult(memberService.getAllMember());
    }
}
