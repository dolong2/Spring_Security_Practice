package com.practice.security.configuration.security;

import com.practice.security.configuration.security.handler.CustomAccessDeniedHandler;
import com.practice.security.configuration.security.handler.CustomAuthenticationEntryPointHandler;
import com.practice.security.configuration.security.jwt.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration{

    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().antMatchers("/v1/api-docs")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/swagger-ui.html")
                .antMatchers("configuration/**")
                .antMatchers("/webjars/**")
                .antMatchers("/public")
                .and()
                .ignoring()
                .antMatchers("/h2-console/**/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        System.out.println("jwtRequestFilter = " + jwtRequestFilter);
        http
                .cors()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                    .antMatchers("/v1/admin/**").hasRole("ADMIN")
                    .antMatchers("/v1/member/**").hasRole("MEMBER")
                    .anyRequest().permitAll()
                .and()
                .exceptionHandling()
                    .accessDeniedHandler(new CustomAccessDeniedHandler())
                    .authenticationEntryPoint(new CustomAuthenticationEntryPointHandler())
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
}
