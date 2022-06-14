package com.practice.security.service;

import com.practice.security.dto.request.MemberReqDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Test
    public void joinTest(){
        MemberReqDto memberReqDto = new MemberReqDto("test@gmail.com", "1234", "조재영");
        Long join = memberService.join(memberReqDto);
        Assertions.assertThat(join).isEqualTo(1L);
    }
}