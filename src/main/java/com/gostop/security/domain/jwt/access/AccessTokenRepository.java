package com.gostop.security.domain.jwt.access;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccessTokenRepository extends CrudRepository<AccessToken, String> {
    Optional<AccessToken> findByAccountId(String accountId);
    Optional<AccessToken> findByAccessToken(String accessToken);
}
