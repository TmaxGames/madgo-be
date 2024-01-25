package com.gostop.security.domain.jwt;

import com.gostop.security.domain.account.Account;
import com.gostop.security.domain.account.AccountRepository;
import com.gostop.security.global.dto.requset.AccountCreateRequestDto;
import com.gostop.security.global.dto.requset.TokenCreateRequestDto;
import com.gostop.security.global.dto.response.JwtIssueResponseDto;
import com.gostop.security.global.exception.account.DuplicatedIdException;
import com.gostop.security.global.exception.account.InvalidAccountException;
import com.gostop.security.global.exception.token.InvalidTokenException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final AccountRepository accountRepository;
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

    public JwtIssueResponseDto refresh(TokenCreateRequestDto tokenCreateRequestDto, HttpServletRequest request) {
        Account account = accountRepository.findByAccountId(tokenCreateRequestDto.getId()).orElseThrow(InvalidTokenException::new);
        String token = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("REFRESH_TOKEN"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(InvalidAccountException::new);

        if(!jwtUtil.validateToken(token, account, JwtType.REFRESH_TOKEN)){
            throw new InvalidTokenException();
        }

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
