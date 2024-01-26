package com.gostop.security.global.exception;

import lombok.Getter;

public enum ErrorCode {

    DUPLICATED_ID(403, "동일 아이디가 존재합니다."),
    INVALID_ACCOUNT(403, "아이디 혹은 비밀번호가 일치하지 않습니다."),
    SESSION_EXPIRED(403, "세션이 만료되었습니다."),
    SESSION_ALREADY_EXIST(403, "이미 로그인 되어있는 계정입니다."),
    SESSION_NOT_FOUND(403, "세션을 찾을 수 없습니다."),
    SESSION_NOT_LOGINED(403, "로그인이 되어있지 않은 계정입니다."),
    INVALID_TOKEN(401, "잘못된 토큰 입니다."),
    TOKEN_EXPIRED(401, "토큰이 만료되었습니다.");

    @Getter
    private final String message;
    private final int status;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

}
