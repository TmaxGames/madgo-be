package com.gostop.security.exception.session;


import com.gostop.security.exception.CustomException;
import com.gostop.security.exception.ErrorCode;

public class SessionNotLoginedException extends CustomException {
    public SessionNotLoginedException() {super(ErrorCode.SESSION_NOT_LOGINED);}
}