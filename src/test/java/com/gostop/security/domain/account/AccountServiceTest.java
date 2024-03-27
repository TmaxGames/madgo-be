package com.gostop.security.domain.account;

import com.gostop.security.global.dto.requset.AccountCreateRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    AccountRepository accountRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    AccountService accountService;

    @Test
    @DisplayName("[Account Service] Account 생성에 성공한다.")
    void testSignupSuccess(){
        //given
        AccountCreateRequestDto accountCreateDto = new AccountCreateRequestDto("testEmail", "testPassword","testNickname");

        //when
        when(accountRepository.findByEmail(accountCreateDto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(accountCreateDto.getPassword())).thenReturn("encodedPassword");

        //then
        assertDoesNotThrow(() -> accountService.signup(accountCreateDto));
    }
}
