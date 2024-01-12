package com.security.gostop.repository.redis;

import com.security.gostop.entity.redis.AccountSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends CrudRepository<AccountSession, String> {
    AccountSession findByAccountId(String accountId);
}
