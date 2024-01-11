package com.security.gostop.service;

import com.security.gostop.dto.requset.SessionLoginRequestDto;
import com.security.gostop.dto.response.SessionLoginResponseDto;
import com.security.gostop.entity.rdb.Account;
import com.security.gostop.entity.redis.AccountSession;
import com.security.gostop.exception.account.InvalidAccountException;
import com.security.gostop.exception.session.SessionAlreadyExistException;
import com.security.gostop.repository.rdb.AccountRepository;
import com.security.gostop.repository.redis.SessionRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final AccountRepository accountRepository;
    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SessionLoginResponseDto login(@RequestBody SessionLoginRequestDto sessionLoginRequestDto, HttpSession session){
        Account account = accountRepository.findByAccountId(sessionLoginRequestDto.getAccountId());
        if(!passwordEncoder.matches(sessionLoginRequestDto.getPassword(), account.getPassword())){
            //전달 받을때는 암호화 x, DB내용은 암호화
            throw new InvalidAccountException();
        }
        if(sessionRepository.findByAccountId(sessionLoginRequestDto.getAccountId()) != null){
            //이미 로그인이 되어있는 상황
            throw new SessionAlreadyExistException();
        }

        AccountSession accountSession = AccountSession.builder()
                .id(session.getId())
                .AccountId(sessionLoginRequestDto.getAccountId())
                .expirationDateTime(LocalDateTime.now().plusMinutes(60))
                .build();

        return SessionLoginResponseDto.builder()
                .accountId(accountSession.getAccountId())
                .name(account.getName())
                .build();
    }
}