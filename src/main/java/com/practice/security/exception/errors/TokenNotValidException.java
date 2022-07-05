package com.practice.security.exception.errors;

import com.practice.security.exception.ErrorCode;
import lombok.Getter;

@Getter
public class TokenNotValidException extends RuntimeException {
    private ErrorCode errorCode;

    public TokenNotValidException(String msg, ErrorCode errorCode){
        super(msg);
        this.errorCode=errorCode;
    }
}
