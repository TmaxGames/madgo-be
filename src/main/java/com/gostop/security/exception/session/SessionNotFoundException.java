package com.gostop.security.exception.session;


import com.gostop.security.exception.CustomException;
import com.gostop.security.exception.ErrorCode;

public class SessionNotFoundException extends CustomException {
    public SessionNotFoundException() {super(ErrorCode.SESSION_EXPIRED);}
}