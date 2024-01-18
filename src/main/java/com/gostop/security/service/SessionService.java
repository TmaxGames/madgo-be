package com.gostop.security.service;

import com.gostop.security.dto.requset.SessionLoginRequestDto;
import com.gostop.security.dto.requset.SessionLogoutRequestDto;
import com.gostop.security.dto.response.SessionLoginResponseDto;
import com.gostop.security.dto.response.SessionLogoutResponseDto;
import com.gostop.security.entity.redis.AccountSession;
import com.gostop.security.exception.account.InvalidAccountException;
import com.gostop.security.exception.session.SessionNotLoginedException;
import com.gostop.security.repository.rdb.AccountRepository;
import com.gostop.security.repository.redis.SessionRepository;
import com.gostop.security.entity.rdb.Account;
import com.gostop.security.exception.session.SessionAlreadyExistException;
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
        Account account = accountRepository.findByAccountId(sessionLoginRequestDto.getAccountId()).orElseThrow(InvalidAccountException::new);
        if(!passwordEncoder.matches(sessionLoginRequestDto.getPassword(), account.getPassword())){
            //전달 받을때는 암호화 x, DB내용은 암호화
            throw new InvalidAccountException();
        }

        if(sessionRepository.findByAccountId(sessionLoginRequestDto.getAccountId()).isPresent()){
            //이미 로그인이 되어있는 상황
            throw new SessionAlreadyExistException();
        }

        AccountSession accountSession = AccountSession.builder()
                .sessionId(session.getId())
                .accountId(sessionLoginRequestDto.getAccountId())
                .expirationDateTime(LocalDateTime.now().plusMinutes(60))
                .build();
        sessionRepository.save(accountSession);

        return SessionLoginResponseDto.builder()
                .accountId(accountSession.getAccountId())
                .name(account.getName())
                .build();
    }

    @Transactional
    public SessionLogoutResponseDto logout(@RequestBody SessionLogoutRequestDto sessionLoginRequestDto, HttpSession session){
        accountRepository.findByAccountId(sessionLoginRequestDto.getAccountId()).orElseThrow(InvalidAccountException::new);

        AccountSession accountSession = sessionRepository.findByAccountId(sessionLoginRequestDto.getAccountId()).orElseThrow(SessionNotLoginedException::new);

        sessionRepository.delete(accountSession);

        return SessionLogoutResponseDto.builder()
                .accountId(accountSession.getAccountId())
                .build();
    }
}