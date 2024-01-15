package com.security.gostop.controller;

import com.security.gostop.dto.ResponseDto;
import com.security.gostop.dto.requset.SessionLoginRequestDto;
import com.security.gostop.dto.requset.SessionLogoutRequestDto;
import com.security.gostop.dto.response.SessionLoginResponseDto;
import com.security.gostop.exception.ErrorResponse;
import com.security.gostop.exception.account.DuplicatedIdException;
import com.security.gostop.exception.account.InvalidAccountException;
import com.security.gostop.exception.session.SessionAlreadyExistException;
import com.security.gostop.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/security/v1/session")
public class SessionController {
    private final SessionService sessionService;
    @PostMapping("/login")
    @Operation(
            summary = "로그인 api",
            description = "유저이름과 비밀번호를 입력하면 로그인 요청이 전송된다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "성공 시 ok 반환 및 Id, 닉네임 반환",
                    content = @Content(schema = @Schema(implementation = SessionLoginResponseDto.class))),
            @ApiResponse(
                    responseCode = "403",
                    description = "존재하지 않는 ID일 경우, 403 반환",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "403",
                    description = "이미 로그인 중인 ID일 경우, 403 반환",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseDto login(@RequestBody SessionLoginRequestDto sessionLoginRequestDto, HttpServletRequest request){
        return ResponseDto.ok("loginInfo", sessionService.login(sessionLoginRequestDto, request.getSession()));
    }

    @PostMapping("/logout")
    @Operation(
            summary = "로그아웃 api",
            description = "유저 Id를 입력하면 로그아웃 요청이 전송된다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "성공 시 ok 반환 및 Id, 닉네임 반환",
                    content = @Content(schema = @Schema(implementation = SessionLoginResponseDto.class))),
            @ApiResponse(
                    responseCode = "403",
                    description = "존재하지 않는 ID일 경우, 403 반환",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "403",
                    description = "이미 로그인 중인 ID가 아닐 경우 경우, 403 반환",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseDto logout(@RequestBody SessionLogoutRequestDto sessionLogoutRequestDto, HttpServletRequest request){
        return ResponseDto.ok("logoutInfo", sessionService.logout(sessionLogoutRequestDto, request.getSession()));
    }
}
