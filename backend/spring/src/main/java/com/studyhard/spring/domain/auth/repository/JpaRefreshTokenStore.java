package com.studyhard.spring.domain.auth.repository;

import com.studyhard.spring.domain.auth.entity.RefreshToken;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaRefreshTokenStore implements RefreshTokenStore {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void save(Long userId, String tokenHash, LocalDateTime expiresAt) {
        refreshTokenRepository.save(RefreshToken.builder()
            .userId(userId)
            .tokenHash(tokenHash)
            .expiresAt(expiresAt)
            .build());
    }

    @Override
    public Optional<Long> findUserId(String tokenHash) {
        return refreshTokenRepository.findByTokenHash(tokenHash)
            .filter(token -> token.getExpiresAt().isAfter(LocalDateTime.now()))
            .map(RefreshToken::getUserId);
    }

    @Override
    public boolean delete(String tokenHash) {
        return refreshTokenRepository.deleteByTokenHash(tokenHash) > 0;
    }
}
