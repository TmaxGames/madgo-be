package com.security.gostop.controller;

import com.security.gostop.dto.ResponseDto;
import com.security.gostop.dto.requset.AccountCreateRequestDto;
import com.security.gostop.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/security/v1/Account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/sign-up")
    public ResponseDto signup(@RequestBody AccountCreateRequestDto accountCreateRequestDto){
        accountService.signup(accountCreateRequestDto);
        return ResponseDto.ok();
    }
}
