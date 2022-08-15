package com.practice.security.domain.member.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface TokenRefreshService {
    Map refresh(String refreshToken);
}
