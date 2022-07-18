package com.practice.security.controller;

import com.practice.security.dto.response.MemberResDto;
import com.practice.security.service.AdminService;
import com.practice.security.service.MemberService;
import com.practice.security.util.response.ResponseService;
import com.practice.security.util.response.result.CommonResultResponse;
import com.practice.security.util.response.result.ListResultResponse;
import com.practice.security.util.response.result.SingleResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final MemberService memberService;
    private final AdminService adminService;
    private final ResponseService responseService;

    @GetMapping("/memberInfo/{memberIdx}")
    public SingleResultResponse<MemberResDto> getOneMember(@PathVariable Long memberIdx){
        return responseService.getSingleResult(memberService.getMemberByIdx(memberIdx));
    }

    @GetMapping("/memberInfo")
    public ListResultResponse<MemberResDto> getAllMember(){
        return responseService.getListResult(memberService.getAllMember());
    }

    @PatchMapping("/grant/admin/{memberIdx}")
    public CommonResultResponse grantRole(@PathVariable Long memberIdx){
        adminService.grantRole(memberIdx);
        return responseService.getSuccessResult();
    }
}
