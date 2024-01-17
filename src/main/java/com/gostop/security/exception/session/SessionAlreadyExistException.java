package com.gostop.security.exception.session;


import com.gostop.security.exception.CustomException;
import com.gostop.security.exception.ErrorCode;

public class SessionAlreadyExistException extends CustomException {
    public SessionAlreadyExistException() {super(ErrorCode.SESSION_ALREADY_EXIST);}
}