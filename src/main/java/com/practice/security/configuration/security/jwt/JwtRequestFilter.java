package com.practice.security.configuration.security.jwt;

import com.practice.security.configuration.security.auth.MyUserDetailService;
import com.practice.security.exception.ErrorCode;
import com.practice.security.exception.errors.TokenExpiredException;
import com.practice.security.exception.errors.TokenNotValidException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final MyUserDetailService memberService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader("Authorization");
        if(accessToken != null){
            if(tokenProvider.isTokenExpired(accessToken)){
                throw new TokenExpiredException("AccessToken is expired", ErrorCode.TOKEN_EXPIRED);
            }
            else if(!tokenProvider.getTokenType(accessToken).equals("accessToken")){
                throw new TokenNotValidException("Token is not valid", ErrorCode.TOKEN_NOT_VALID);
            }
            String email = tokenProvider.getUserEmail(accessToken);
            registerSecurityContext(request, email);
        }
        filterChain.doFilter(request, response);
    }

    private void registerSecurityContext(HttpServletRequest request, String email) {
        UserDetails userDetails = memberService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
