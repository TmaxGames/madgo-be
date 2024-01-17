package com.gostop.security.exception.account;


import com.gostop.security.exception.CustomException;
import com.gostop.security.exception.ErrorCode;

public class DuplicatedIdException extends CustomException {
    public DuplicatedIdException() {super(ErrorCode.DUPLICATED_ID);}
}