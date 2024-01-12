package com.security.gostop.exception.session;


import com.security.gostop.exception.CustomException;
import com.security.gostop.exception.ErrorCode;

public class SessionNotFoundException extends CustomException {
    public SessionNotFoundException() {super(ErrorCode.SESSION_EXPIRED);}
}