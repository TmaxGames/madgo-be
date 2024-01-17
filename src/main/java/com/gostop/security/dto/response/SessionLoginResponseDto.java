package com.gostop.security.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "로그인 완료 응답")
public class SessionLoginResponseDto {
    @Schema(description = "로그인이 완료된 계정 아이디")
    private String accountId;
    @Schema(description = "로그인이 완료된 계정 닉네임")
    private String name;
}
