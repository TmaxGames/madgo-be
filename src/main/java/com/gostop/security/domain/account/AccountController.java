package com.gostop.security.domain.account;

import com.gostop.security.global.dto.ResponseDto;
import com.gostop.security.global.exception.ErrorResponse;
import com.gostop.security.domain.account.AccountService;
import com.gostop.security.global.dto.requset.AccountCreateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/security/v1/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/sign-up")
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
    public ResponseDto signup(@RequestBody AccountCreateRequestDto accountCreateRequestDto){
        accountService.signup(accountCreateRequestDto);
        return ResponseDto.ok();
    }
}
