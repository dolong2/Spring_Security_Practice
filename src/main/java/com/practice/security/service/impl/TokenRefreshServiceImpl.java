package com.practice.security.service.impl;

import com.practice.security.configuration.security.jwt.TokenProvider;
import com.practice.security.exception.ErrorCode;
import com.practice.security.exception.errors.TokenExpiredException;
import com.practice.security.service.TokenRefreshService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenRefreshServiceImpl implements TokenRefreshService {
    private final TokenProvider tokenProvider;

    @Override
    public Map refresh(String refreshToken) {
        if(tokenProvider.isTokenExpired(refreshToken)){
            throw new TokenExpiredException("Refresh token is expired", ErrorCode.TOKEN_EXPIRED);
        }
        String email = tokenProvider.getUserEmail(refreshToken);
        String accessToken = tokenProvider.generateAccessToken(email);
        Map<String, String> response = new HashMap<>();
        response.put("accessToken", accessToken);
        return response;
    }
}
