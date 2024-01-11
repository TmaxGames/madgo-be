package com.security.gostop.exception.session;


import com.security.gostop.exception.CustomException;
import com.security.gostop.exception.ErrorCode;

public class SessionExpiredException extends CustomException {
    public SessionExpiredException() {super(ErrorCode.SESSION_EXPIRED);}
}