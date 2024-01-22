package com.gostop.security.global.exception.account;


import com.gostop.security.global.exception.CustomException;
import com.gostop.security.global.exception.ErrorCode;

public class DuplicatedIdException extends CustomException {
    public DuplicatedIdException() {super(ErrorCode.DUPLICATED_ID);}
}