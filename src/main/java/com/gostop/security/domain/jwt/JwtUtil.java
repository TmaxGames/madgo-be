package com.gostop.security.domain.jwt;

import com.gostop.security.domain.account.Account;
import com.gostop.security.domain.jwt.refresh.RefreshToken;
import com.gostop.security.domain.jwt.refresh.RefreshTokenRepository;
import com.gostop.security.global.util.DateToLocalDateTime;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtUtil {
    private final RefreshTokenRepository refreshTokenRepository;
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    public Long access_token_minute = 1L;
    public Long refresh_token_minute = 60L;

    public String generateToken(String userId, JwtType type) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("jwtType", type);
        switch (type){
            case ACCESS_TOKEN -> {
                return createToken(claims, userId, access_token_minute).getRefreshToken();
            }
            case REFRESH_TOKEN -> {
                RefreshToken refreshToken = createToken(claims, userId, refresh_token_minute);
                refreshTokenRepository.save(refreshToken);
                return refreshToken.getRefreshToken();
            }
            default -> throw new RuntimeException("잘못된 토큰 타입입니다.");
        }
    }

    private RefreshToken createToken(Map<String, Object> claims, String userName, Long minute) {
        Date issueTime = new Date(System.currentTimeMillis());
        Date expireTime = new Date(System.currentTimeMillis() + 1000 * 60 * minute);
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(issueTime)
                .setExpiration(expireTime)
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();

        return RefreshToken.builder()
                .expirationDateTime(DateToLocalDateTime.convert(expireTime))
                .refreshToken(token)
                .accountId(userName)
                .build();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean isValidateAccessToken(String token, Account account, JwtType expect) {
        final String userId = extractUserId(token);
        final String jwtType = (String) extractAllClaims(token).get("jwtType");
        return userId.equals(account.getAccountId()) && !isTokenExpired(token) && jwtType.equals(expect.toString());
    }

    public Boolean isValidUsersNotExpiredRefreshToken(String userId, RefreshToken refreshToken){
        return !refreshToken.isExpired() && refreshToken.getAccountId().equals(userId);
    }
}
