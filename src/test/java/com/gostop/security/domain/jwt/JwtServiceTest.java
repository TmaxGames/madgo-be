package com.gostop.security.domain.jwt;

import com.gostop.security.domain.account.Account;
import com.gostop.security.domain.account.AccountRepository;
import com.gostop.security.domain.account.AccountService;
import com.gostop.security.domain.jwt.access.AccessTokenRepository;
import com.gostop.security.domain.jwt.refresh.RefreshTokenRepository;
import com.gostop.security.global.dto.requset.AccountCreateRequestDto;
import com.gostop.security.global.dto.requset.TokenCreateRequestDto;
import com.gostop.security.global.exception.account.DuplicatedIdException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {
    @Mock
    RefreshTokenRepository refreshTokenRepository;
    @Mock
    AccessTokenRepository accessTokenRepository;
    @Mock
    AccountRepository accountRepository;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtUtil jwtUtil;
    @InjectMocks
    JwtService jwtService;

    @Test
    @DisplayName("[JwtService] 토큰 발급에 성공한다.")
    void testSignupSuccess(){
        //given
        TokenCreateRequestDto tokenCreateDto = TokenCreateRequestDto.builder()
                .email("testEmail")
                .password("testPassword")
                .build();

        //when


        //then
    }
}
