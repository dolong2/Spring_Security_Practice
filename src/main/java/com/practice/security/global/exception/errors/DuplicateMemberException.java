package com.practice.security.global.exception.errors;

import com.practice.security.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class DuplicateMemberException extends RuntimeException{

    private ErrorCode errorCode;

    public DuplicateMemberException(String msg, ErrorCode errorCode){
        super(msg);
        this.errorCode = errorCode;
    }
}
