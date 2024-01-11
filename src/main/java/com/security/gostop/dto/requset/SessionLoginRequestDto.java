package com.security.gostop.dto.requset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionLoginRequestDto {
    private String accountId;
    private String password;
}
