package com.gostop.security.domain.jwt.refresh;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Builder
@Getter
@RedisHash("RefreshToken")
public class RefreshToken {
    @Id
    private String id;
    @Indexed
    private String accountId;
    @Indexed
    private String refreshToken;
    private LocalDateTime expirationDateTime;

    public void refreshExpirationTime() {
        this.expirationDateTime = LocalDateTime.now().plusMinutes(30);
    }

    public boolean isExpired() {
        return this.expirationDateTime.isBefore(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "RefreshToken{" +
                "id='" + id + '\'' +
                ", accountId='" + accountId + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expirationDateTime=" + expirationDateTime +
                '}';
    }
}
