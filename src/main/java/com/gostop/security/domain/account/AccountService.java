package com.gostop.security.domain.account;

import com.gostop.security.global.exception.account.DuplicatedIdException;
import com.gostop.security.global.dto.requset.AccountCreateRequestDto;
import com.gostop.security.global.exception.account.InvalidAccountException;
import com.gostop.security.domain.jwt.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public void signup(AccountCreateRequestDto accountCreateRequestDto) {
        if (accountRepository.findByAccountId(accountCreateRequestDto.getId()).isPresent()) {
            //중복 아이디
            throw new DuplicatedIdException();
        }
        String encodedPassword = passwordEncoder.encode(accountCreateRequestDto.getPassword());

        Account account = Account.builder()
                .accountId(accountCreateRequestDto.getId())
                .name(accountCreateRequestDto.getName())
                .password(encodedPassword)
                .role("PLAYER")
                .win(0L)
                .lose(0L)
                .money(10000L)
                .score(0L)
                .profile_url("asd")
                .build();

        accountRepository.save(account);
    }
}