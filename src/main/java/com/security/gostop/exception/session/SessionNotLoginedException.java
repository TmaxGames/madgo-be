package com.security.gostop.exception.session;


import com.security.gostop.exception.CustomException;
import com.security.gostop.exception.ErrorCode;

public class SessionNotLoginedException extends CustomException {
    public SessionNotLoginedException() {super(ErrorCode.SESSION_NOT_LOGINED);}
}