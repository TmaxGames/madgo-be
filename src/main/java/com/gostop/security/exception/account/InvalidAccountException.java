package com.gostop.security.exception.account;


import com.gostop.security.exception.CustomException;
import com.gostop.security.exception.ErrorCode;

public class InvalidAccountException extends CustomException {
    public InvalidAccountException() {super(ErrorCode.INVALID_ACCOUNT);}
}