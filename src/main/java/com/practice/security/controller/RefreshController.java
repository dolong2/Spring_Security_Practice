package com.practice.security.controller;

import com.practice.security.service.TokenRefreshService;
import com.practice.security.util.response.ResponseService;
import com.practice.security.util.response.result.SingleResultResponse;
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
