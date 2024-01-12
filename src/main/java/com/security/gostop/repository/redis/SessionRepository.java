package com.security.gostop.repository.redis;

import com.security.gostop.entity.rdb.Account;
import com.security.gostop.entity.redis.AccountSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends CrudRepository<AccountSession, String> {
    AccountSession findByAccountId(String accountId);
}
