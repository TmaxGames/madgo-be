package com.gostop.security.global.dto.requset;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "계정 생성 DTO")
public class AccountCreateRequestDto {
    @Schema(description = "아이디")
    private String id;
    @Schema(description = "비밀번호")
    private String password;
}
