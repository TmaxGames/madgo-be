package com.security.gostop.controller;

import com.security.gostop.dto.ResponseDto;
import com.security.gostop.dto.requset.SessionLoginRequestDto;
import com.security.gostop.service.SessionService;
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
    public ResponseDto login(@RequestBody SessionLoginRequestDto sessionLoginRequestDto, HttpServletRequest request){
        return ResponseDto.ok("data", sessionService.login(sessionLoginRequestDto, request.getSession()));
    }
}
