package com.security.gostop.exception.account;


import com.security.gostop.exception.CustomException;
import com.security.gostop.exception.ErrorCode;

public class InvalidAccountException extends CustomException {
    public InvalidAccountException() {super(ErrorCode.INVALID_ACCOUNT);}
}