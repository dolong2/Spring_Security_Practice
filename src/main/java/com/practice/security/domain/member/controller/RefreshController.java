package com.practice.security.domain.member.controller;

import com.practice.security.domain.member.service.TokenRefreshService;
import com.practice.security.global.util.response.ResponseService;
import com.practice.security.global.util.response.result.SingleResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/")
public class RefreshController {

    private final ResponseService responseService;
    private final TokenRefreshService tokenRefreshService;

    @PostMapping("/refresh")
    public SingleResultResponse refreshAccessToken(@RequestHeader String refreshToken){
        return responseService.getSingleResult(tokenRefreshService.refresh(refreshToken));
    }
}
