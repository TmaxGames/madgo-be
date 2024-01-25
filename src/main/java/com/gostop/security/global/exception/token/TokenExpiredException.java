package com.gostop.security.global.exception.token;


import com.gostop.security.global.exception.CustomException;
import com.gostop.security.global.exception.ErrorCode;

public class TokenExpiredException extends CustomException {
    public TokenExpiredException() {super(ErrorCode.TOKEN_EXPIRED);}
}