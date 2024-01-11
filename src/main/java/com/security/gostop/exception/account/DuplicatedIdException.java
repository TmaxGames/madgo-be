package com.security.gostop.exception.account;


import com.security.gostop.exception.CustomException;
import com.security.gostop.exception.ErrorCode;

public class DuplicatedIdException extends CustomException {
    public DuplicatedIdException() {super(ErrorCode.DUPLICATED_ID);}
}