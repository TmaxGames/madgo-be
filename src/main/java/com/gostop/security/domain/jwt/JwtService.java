package com.gostop.security.domain.jwt;

import com.gostop.security.global.dto.requset.AccountCreateRequestDto;
import com.gostop.security.global.exception.account.InvalidAccountException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    public String authenticateAndGetToken(AccountCreateRequestDto accountCreateRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(accountCreateRequestDto.getName(), accountCreateRequestDto.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtUtil.generateToken(accountCreateRequestDto.getName());
        } else {
            throw new InvalidAccountException();
        }
    }
}
