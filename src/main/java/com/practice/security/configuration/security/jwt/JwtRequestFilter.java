package com.practice.security.configuration.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;
    private final MyUserDetailService memberService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader("Authorization");
        String refreshToken = request.getHeader("RefreshToken");
        if(accessToken != null){
            if(tokenProvider.isTokenExpired(accessToken) && refreshToken != null && !tokenProvider.isTokenExpired(refreshToken) && tokenProvider.getTokenType(refreshToken).equals("refreshToken")){
                writeBody(response, generateNewToken(refreshToken));
                return;
            }
            else if(tokenProvider.isTokenExpired(refreshToken)){
                throw new TokenExpiredException("RefreshToken is expired", ErrorCode.TOKEN_EXPIRED);
            }
            if(!tokenProvider.getTokenType(accessToken).equals("accessToken")){
                throw new TokenNotValidException("Token is not valid", ErrorCode.TOKEN_NOT_VALID);
            }
            String email = tokenProvider.getUserEmail(accessToken);
            registerSecurityContext(request, email);
        }
        filterChain.doFilter(request, response);
    }

    private String generateNewToken(String refreshToken) {
        return tokenProvider.generateAccessToken(tokenProvider.getUserEmail(refreshToken));
    }

    private void registerSecurityContext(HttpServletRequest request, String email) {
        UserDetails userDetails = memberService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    private void writeBody(HttpServletResponse response, String accessToken) throws IOException {
        String json = getJson();
        response.setStatus(401);
        response.setHeader("AccessToken", accessToken);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("Application/json");
        response.getWriter().write(json);
    }

    private String getJson() throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("msg", "AccessToken is expired");
        map.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        String json = objectMapper.writeValueAsString(map);
        return json;
    }
}
