package com.gostop.security.global.dto.requset;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "토큰 생성 DTO")
public class TokenCreateRequestDto {
    @Schema(description = "아이디")
    private String id;
    @Schema(description = "비밀번호")
    private String password;
}
