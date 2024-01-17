package com.gostop.security.exception.session;


import com.gostop.security.exception.CustomException;
import com.gostop.security.exception.ErrorCode;

public class SessionExpiredException extends CustomException {
    public SessionExpiredException() {super(ErrorCode.SESSION_EXPIRED);}
}