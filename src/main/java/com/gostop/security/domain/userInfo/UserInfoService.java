package com.gostop.security.domain.userInfo;

import com.gostop.security.domain.account.Account;
import com.gostop.security.global.exception.account.InvalidAccountException;
import com.gostop.security.domain.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoService implements UserDetailsService {
    private final AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Account account = accountRepository.findByAccountId(id).orElseThrow(InvalidAccountException::new);
        return new UserInfoDetails(account);
    }
}
