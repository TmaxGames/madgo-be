package com.security.gostop.entity.redis;

import com.security.gostop.exception.session.SessionExpiredException;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Builder
@Getter
@RedisHash("AccountSession")
public class AccountSession {
    @Id
    private String id;
    @Indexed
    private String AccountId;
    private LocalDateTime expirationDateTime;

    public void refreshExpirationTime() {
        this.expirationDateTime = LocalDateTime.now().plusMinutes(30);
    }

    public boolean isExpiredSession() {
        return this.expirationDateTime.isBefore(LocalDateTime.now());
    }
}
