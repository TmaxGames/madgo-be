package com.gostop.security.domain.jwt;

import com.gostop.security.global.dto.requset.AccountCreateRequestDto;
import com.gostop.security.global.dto.requset.TokenCreateRequestDto;
import com.gostop.security.global.dto.response.JwtIssueResponseDto;
import com.gostop.security.global.exception.account.DuplicatedIdException;
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
    public JwtIssueResponseDto authenticateAndGetToken(TokenCreateRequestDto tokenCreateRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(tokenCreateRequestDto.getId(), tokenCreateRequestDto.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return JwtIssueResponseDto.builder()
                    .accessToken(jwtUtil.generateToken(tokenCreateRequestDto.getId(), JwtType.ACCESS_TOKEN))
                    .refreshToken(jwtUtil.generateToken(tokenCreateRequestDto.getId(), JwtType.REFRESH_TOKEN))
                    .build();
        } else {
            throw new InvalidAccountException();
        }
    }
}
