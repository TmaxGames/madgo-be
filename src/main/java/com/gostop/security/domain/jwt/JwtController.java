package com.gostop.security.domain.jwt;

import com.gostop.security.global.dto.ResponseDto;
import com.gostop.security.global.dto.requset.AccountCreateRequestDto;
import com.gostop.security.global.dto.requset.TokenCreateRequestDto;
import com.gostop.security.global.dto.response.AccessTokenResponseDto;
import com.gostop.security.global.dto.response.JwtIssueResponseDto;
import com.gostop.security.global.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/security/v1/jwt")
public class JwtController {
    private final JwtService jwtService;

    @PostMapping("/login")
    @Operation(
            summary = "로그인 요청을 하고, refresh 토큰과 access 토큰이 발행되는 api",
            description = "유저이름과 비밀번호와 PLAYER 롤에 기반하여 jwt 발행, access 토큰은 json에 refresh 토큰은 쿠키에 삽입"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "성공 시 ok와 jwt 반환"),
            @ApiResponse(
                    responseCode = "403",
                    description = "존재하지 않는 아이디, 비밀번호, 닉네임 일 경우, 403 반환",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<AccessTokenResponseDto> issue(@RequestBody TokenCreateRequestDto tokenCreateRequestDto, HttpServletResponse response){
        JwtIssueResponseDto dto = jwtService.authenticateAndGetToken(tokenCreateRequestDto);
        AccessTokenResponseDto responseDto = AccessTokenResponseDto.builder()
                .accessToken(dto.getAccessToken())
                .build();
        Cookie tokenCookie = new Cookie("REFRESH_TOKEN", dto.getRefreshToken());
        response.addCookie(tokenCookie);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/refresh")
    @Operation(
            summary = "jwt 리프레시 api",
            description = "유저이름과 비밀번호와 PLAYER 롤에 기반하여 jwt 발행, access 토큰은 json에 refresh 토큰은 쿠키에 삽"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "성공 시 ok와 jwt 반환"),
            @ApiResponse(
                    responseCode = "403",
                    description = "존재하지 않는 아이디, 비밀번호, 닉네임 일 경우, 403 반환",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<AccessTokenResponseDto> refresh(@RequestBody TokenCreateRequestDto tokenCreateRequestDto, HttpServletRequest request, HttpServletResponse response){
        JwtIssueResponseDto dto = jwtService.refresh(tokenCreateRequestDto, request);
        AccessTokenResponseDto responseDto = AccessTokenResponseDto.builder()
                .accessToken(dto.getAccessToken())
                .build();
        Cookie tokenCookie = new Cookie("REFRESH_TOKEN", dto.getRefreshToken());
        response.addCookie(tokenCookie);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/test")
    @Operation(
            summary = "테스트 api",
            description = "jwt를 통해 잘 통신했다면 good을 리턴"
    )
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("good");
    }
}
