package com.gostop.security.global.exception.account;


import com.gostop.security.global.exception.CustomException;
import com.gostop.security.global.exception.ErrorCode;

public class InvalidAccountException extends CustomException {
    public InvalidAccountException() {super(ErrorCode.INVALID_ACCOUNT);}
}