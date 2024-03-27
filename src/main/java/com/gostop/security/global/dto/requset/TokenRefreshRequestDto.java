package com.gostop.security.global.dto.requset;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "토큰 리프레시 DTO")
public class TokenRefreshRequestDto {
    @NotNull
    @Schema(description = "아이디")
    private String email;
}
