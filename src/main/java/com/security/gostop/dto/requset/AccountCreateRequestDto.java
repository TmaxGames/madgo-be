package com.security.gostop.dto.requset;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateRequestDto {
    private String id;
    private String password;
    private String name;
}
