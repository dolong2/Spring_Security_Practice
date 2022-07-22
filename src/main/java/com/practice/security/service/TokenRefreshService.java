package com.practice.security.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface TokenRefreshService {
    Map refresh(String refreshToken);
}
