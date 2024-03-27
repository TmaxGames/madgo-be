package com.gostop.security.global.dto.requset;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "계정 생성 DTO")
public class AccountCreateRequestDto {
    @NotNull
    @Schema(description = "아이디")
    private String email;
    @NotNull
    @Schema(description = "비밀번호")
    private String password;
    @NotNull
    @Schema(description = "유저이름")
    private String nickname;
}
