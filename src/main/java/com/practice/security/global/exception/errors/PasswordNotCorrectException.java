package com.practice.security.global.exception.errors;

import com.practice.security.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class PasswordNotCorrectException extends RuntimeException{
    private ErrorCode errorCode;

    public PasswordNotCorrectException(String msg, ErrorCode errorCode){
        super(msg);
        this.errorCode=errorCode;
    }
}
