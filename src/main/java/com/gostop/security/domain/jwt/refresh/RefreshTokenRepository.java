package com.gostop.security.domain.jwt.refresh;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByAccountId(String accountId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
