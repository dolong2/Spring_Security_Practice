package com.practice.security.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private boolean success;
    private String msg;
    private int status;
}
