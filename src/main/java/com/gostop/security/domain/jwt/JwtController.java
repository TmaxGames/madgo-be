package com.gostop.security.domain.jwt;

import com.gostop.security.global.dto.ResponseDto;
import com.gostop.security.global.dto.requset.AccountCreateRequestDto;
import com.gostop.security.global.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/security/v1/jwt")
public class JwtController {
    private final JwtService jwtService;

    @PostMapping("/issue")
    @Operation(
            summary = "회원가입 api",
            description = "유저이름과 비밀번호 닉네임을 입력하면 회원가입 요청이 전송된다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "성공 시 ok와 jwt 반환"),
            @ApiResponse(
                    responseCode = "403",
                    description = "이미 존재하는 아이디 일 경우, 403 반환",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseDto issue(@RequestBody AccountCreateRequestDto accountCreateRequestDto){
        return ResponseDto.ok("jwt", jwtService.authenticateAndGetToken(accountCreateRequestDto));
    }
}
