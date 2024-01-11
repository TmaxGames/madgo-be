package com.security.gostop.exception.session;


import com.security.gostop.exception.CustomException;
import com.security.gostop.exception.ErrorCode;

public class SessionAlreadyExistException extends CustomException {
    public SessionAlreadyExistException() {super(ErrorCode.SESSION_ALREADY_EXIST);}
}