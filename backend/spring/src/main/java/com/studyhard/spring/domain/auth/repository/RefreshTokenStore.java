package com.studyhard.spring.domain.auth.repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Storage for refresh token hashes, kept independent of JPA so it can be swapped for Redis
 * (hash as key, userId as value, TTL until expiresAt) without touching AuthService.
 */
public interface RefreshTokenStore {

    void save(Long userId, String tokenHash, LocalDateTime expiresAt);

    /**
     * @return the owner's userId, or empty if the token is unknown or expired
     */
    Optional<Long> findUserId(String tokenHash);

    /**
     * @return true if a token was deleted by this call
     */
    boolean delete(String tokenHash);
}
