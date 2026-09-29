package com.studyhard.spring.global.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.Duration;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private final SecretKey key;
    private final Duration accessTokenExpiration;

    public JwtProvider(JwtProperties properties) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(properties.secret()));
        this.accessTokenExpiration = properties.accessTokenExpiration();
    }

    public String createAccessToken(Long userId) {
        Date now = new Date();
        return Jwts.builder()
            .subject(String.valueOf(userId))
            .issuedAt(now)
            .expiration(new Date(now.getTime() + accessTokenExpiration.toMillis()))
            .signWith(key)
            .compact();
    }

    /**
     * @throws io.jsonwebtoken.JwtException if the token is malformed, tampered with or expired
     */
    public Long parseUserId(String accessToken) {
        String subject = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(accessToken)
            .getPayload()
            .getSubject();
        return Long.valueOf(subject);
    }
}
