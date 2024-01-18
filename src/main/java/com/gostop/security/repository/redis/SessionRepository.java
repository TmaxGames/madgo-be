package com.gostop.security.repository.redis;

import com.gostop.security.entity.redis.AccountSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends CrudRepository<AccountSession, String> {
    Optional<AccountSession> findByAccountId(String accountId);
    Optional<AccountSession> findBySessionId(String sessionId);
}
