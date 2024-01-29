package com.gostop.security.domain.jwt;

import com.gostop.security.domain.account.Account;
import com.gostop.security.domain.account.AccountRepository;
import com.gostop.security.domain.jwt.refresh.RefreshToken;
import com.gostop.security.domain.jwt.refresh.RefreshTokenRepository;
import com.gostop.security.global.dto.requset.TokenCreateRequestDto;
import com.gostop.security.global.dto.requset.TokenRefreshRequestDto;
import com.gostop.security.global.dto.response.JwtIssueResponseDto;
import com.gostop.security.global.exception.account.InvalidAccountException;
import com.gostop.security.global.exception.token.InvalidTokenException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final RefreshTokenRepository refreshTokenRepository;
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

    @Transactional
    public void removeAndDestroyToken(String Authorization, String accountId, HttpServletRequest request){
        String refreshTokenString = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("REFRESH_TOKEN"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(InvalidAccountException::new);

        //access token의 경우 이미 필터에서 검증이 됨
        String[] parsedAccessToken = Authorization.split(" ");
        if(parsedAccessToken.length != 2 || !parsedAccessToken[0].equals("Bearer")){
            throw new InvalidTokenException();
        }
        String accessToken = parsedAccessToken[1];

        Account account = accountRepository.findByAccountId(accountId).orElseThrow(InvalidAccountException::new);
        if(!jwtUtil.isValidateAccessToken(accessToken, account, JwtType.ACCESS_TOKEN)){
            throw new InvalidTokenException();
        }

        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(refreshTokenString).orElseThrow(InvalidTokenException::new);

        if(!jwtUtil.isValidUsersNotExpiredRefreshToken(accountId, refreshToken)){
            throw new InvalidTokenException();
        }
    }

    public JwtIssueResponseDto refresh(String accountId, HttpServletRequest request) {
        String token = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("REFRESH_TOKEN"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(InvalidAccountException::new);

        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token).orElseThrow(InvalidTokenException::new);

        if(!jwtUtil.isValidUsersNotExpiredRefreshToken(accountId, refreshToken)){
            throw new InvalidTokenException();
        }

        return JwtIssueResponseDto.builder()
                .accessToken(jwtUtil.generateToken(accountId, JwtType.ACCESS_TOKEN))
                .refreshToken(jwtUtil.generateToken(accountId, JwtType.REFRESH_TOKEN))
                .build();
    }
}
