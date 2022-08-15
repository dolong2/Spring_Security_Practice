package com.practice.security.domain.member.service;

import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    void grantRole(Long memberIdx);
}
