package com.gostop.security.domain.jwt;

import com.gostop.security.global.dto.ResponseDto;
import com.gostop.security.global.dto.requset.AccountCreateRequestDto;
import com.gostop.security.global.dto.response.JwtIssueResponseDto;
import com.gostop.security.global.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @PostMapping("/issue")
    @Operation(
            summary = "jwt 발행 api",
            description = "유저이름과 비밀번호와 PLAYER 롤에 기반하여 jwt 발행"
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
    public ResponseEntity<JwtIssueResponseDto> issue(@RequestBody AccountCreateRequestDto accountCreateRequestDto){
        return ResponseEntity.ok(jwtService.authenticateAndGetToken(accountCreateRequestDto));
    }

    @GetMapping("/test")
    @Operation(
            summary = "테스트 api",
            description = "jwt를 통해 잘 통신했다면 good을 리턴"
    )
    public ResponseEntity<String> test(){
        System.out.println("asdad");
        return ResponseEntity.ok("good");
    }
}
