package com.gostop.security.domain.jwt.access;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Builder
@Getter
@RedisHash("AccessToken")
public class AccessToken {
    @Id
    private String id;
    @Indexed
    private String accessToken;
    private LocalDateTime expirationDateTime;

    @Override
    public String toString() {
        return "AccessToken{" +
                "id='" + id + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", expirationDateTime=" + expirationDateTime +
                '}';
    }
}
