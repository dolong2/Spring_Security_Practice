package com.practice.security.exception.errors;

import com.practice.security.exception.ErrorCode;
import lombok.Getter;

@Getter
public class TokenExpiredException extends RuntimeException{

    private ErrorCode errorCode;

    public TokenExpiredException(String msg, ErrorCode errorCode){
        super(msg);
        this.errorCode=errorCode;
    }
}
