package com.gostop.security.global.exception.token;


import com.gostop.security.global.exception.CustomException;
import com.gostop.security.global.exception.ErrorCode;

public class InvalidTokenException extends CustomException {
    public InvalidTokenException() {super(ErrorCode.INVALID_TOKEN);}
}