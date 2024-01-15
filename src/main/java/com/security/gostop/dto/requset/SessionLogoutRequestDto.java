package com.security.gostop.dto.requset;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "로그아웃용 DTO")
public class SessionLogoutRequestDto {
    @Schema(description = "계정 아이디")
    private String accountId;
}
