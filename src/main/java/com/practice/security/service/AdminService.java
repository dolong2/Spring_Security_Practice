package com.practice.security.service;

import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    void grantRole(Long memberIdx);
}
