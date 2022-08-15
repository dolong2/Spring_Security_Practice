package com.practice.security.global.exception.errors;

import com.practice.security.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class TokenNotValidException extends RuntimeException {
    private ErrorCode errorCode;

    public TokenNotValidException(String msg, ErrorCode errorCode){
        super(msg);
        this.errorCode=errorCode;
    }
}
