package com.practice.security.service;

import com.practice.security.domain.Member;
import com.practice.security.dto.request.MemberReqDto;
import com.practice.security.dto.request.SignInDto;
import com.practice.security.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import javax.annotation.PostConstruct;
import java.util.Map;

@Profile("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class MemberServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void init(){
        memberRepository.deleteAll();
        MemberReqDto memberReqDto = new MemberReqDto("test@gmail.com", "1234", "조재영");
        memberService.join(memberReqDto);
    }

    @Test
    @Order(1)
    public void joinTest(){
        //given
        MemberReqDto memberReqDto = new MemberReqDto("test1@gmail.com", "1234", "조재영");

        //when
        Long join = memberService.join(memberReqDto);

        //then
        Assertions.assertThat(join).isEqualTo(2L);
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> memberService.join(memberReqDto));
    }

    @Test
    public void loginTest(){
        SignInDto signInDto = new SignInDto("test@gmail.com", "1234");
        Map<String, Object> login = memberService.login(signInDto);
        Member member = memberRepository.findById((Long) login.get("member_id")).get();
        Assertions.assertThat(login.get("member_id")).isEqualTo(member.getId());
    }
}