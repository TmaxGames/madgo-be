package com.gostop.security.domain.jwt;

import com.gostop.security.domain.account.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public String generateToken(String userName, JwtType type) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("jwtType", type);
        switch (type){
            case ACCESS_TOKEN -> {
                return createToken(claims, userName, 30L);
            }
            case REFRESH_TOKEN -> {
                return createToken(claims, userName, 60L);
            }
            default -> throw new RuntimeException("잘못된 토큰 타입입니다.");
        }
    }

    private String createToken(Map<String, Object> claims, String userName, Long minute) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * minute))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
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

    public Boolean validateToken(String token, Account account, JwtType expect) {
        final String userId = extractUserId(token);
        final String jwtType = (String) extractAllClaims(token).get("jwtType");
        return userId.equals(account.getAccountId()) && !isTokenExpired(token) && jwtType.equals(expect.toString());
    }
}
