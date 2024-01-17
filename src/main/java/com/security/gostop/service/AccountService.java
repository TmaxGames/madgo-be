package com.security.gostop.service;

import com.security.gostop.dto.requset.AccountCreateRequestDto;
import com.security.gostop.entity.rdb.Account;
import com.security.gostop.exception.account.DuplicatedIdException;
import com.security.gostop.repository.rdb.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    /*
     * signup
     * 지정 아이디가 존재하는지 확인
     * 비밀번호가 valid 한지 확인
     */
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
                .build();

        accountRepository.save(account);
    }
}
