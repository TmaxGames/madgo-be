package com.gostop.security.domain.account;

import com.gostop.security.global.exception.account.DuplicatedIdException;
import com.gostop.security.global.dto.requset.AccountCreateRequestDto;
import com.gostop.security.global.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public String signup(AccountCreateRequestDto accountCreateRequestDto) {
        if (accountRepository.findByAccountId(accountCreateRequestDto.getId()).isPresent()) {
            //중복 아이디
            throw new DuplicatedIdException();
        }
        String encodedPassword = passwordEncoder.encode(accountCreateRequestDto.getPassword());

        Account account = Account.builder()
                .accountId(accountCreateRequestDto.getId())
                .name(accountCreateRequestDto.getName())
                .password(encodedPassword)
                .build();

        accountRepository.save(account);

        return authenticateAndGetToken(accountCreateRequestDto);
    }

    private String authenticateAndGetToken(AccountCreateRequestDto accountCreateRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(accountCreateRequestDto.getName(), accountCreateRequestDto.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtUtil.generateToken(accountCreateRequestDto.getName());
        }
        else{
            throw new UsernameNotFoundException("잘못된 유저입니다.");
        }
    }
}