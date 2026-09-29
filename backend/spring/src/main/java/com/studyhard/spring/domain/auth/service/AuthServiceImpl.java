package com.studyhard.spring.domain.auth.service;

import com.studyhard.spring.domain.auth.dto.LoginRequest;
import com.studyhard.spring.domain.auth.dto.RefreshTokenRequest;
import com.studyhard.spring.domain.auth.dto.SignupRequest;
import com.studyhard.spring.domain.auth.dto.TokenResponse;
import com.studyhard.spring.domain.auth.repository.RefreshTokenStore;
import com.studyhard.spring.domain.user.dto.UserResponse;
import com.studyhard.spring.domain.user.entity.OauthProvider;
import com.studyhard.spring.domain.user.entity.User;
import com.studyhard.spring.domain.user.repository.UserRepository;
import com.studyhard.spring.global.exception.BusinessException;
import com.studyhard.spring.global.security.JwtProperties;
import com.studyhard.spring.global.security.JwtProvider;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;
    private final RefreshTokenStore refreshTokenStore;

    @Override
    @Transactional
    public UserResponse signup(SignupRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new BusinessException(HttpStatus.CONFLICT, "이미 사용 중인 아이디입니다.");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다.");
        }

        User user = User.builder()
            .username(request.username())
            .passwordHash(passwordEncoder.encode(request.password()))
            .email(request.email())
            .nickname(request.nickname())
            .oauthProvider(OauthProvider.LOCAL)
            .build();

        return UserResponse.from(userRepository.save(user));
    }

    @Override
    @Transactional
    public TokenResponse login(LoginRequest request) {
        // Same message for unknown username and wrong password, so usernames cannot be probed.
        User user = userRepository.findByUsername(request.username())
            .filter(found -> passwordEncoder.matches(request.password(), found.getPasswordHash()))
            .orElseThrow(() -> new BusinessException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다."));
        return issueTokens(user.getUserId());
    }

    /**
     * Rotates the refresh token: the presented one is deleted and a new pair is issued,
     * so a leaked refresh token can be used at most once.
     * The 401 must not roll back the delete, or expired rows would never be cleaned up.
     */
    @Override
    @Transactional(noRollbackFor = BusinessException.class)
    public TokenResponse reissue(RefreshTokenRequest request) {
        String tokenHash = hash(request.refreshToken());
        Optional<Long> userId = refreshTokenStore.findUserId(tokenHash);
        // Delete even when expired so stale rows do not pile up. A false result means a concurrent
        // reissue already consumed this token, which must not yield a second pair.
        boolean deleted = refreshTokenStore.delete(tokenHash);
        if (userId.isEmpty() || !deleted) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "유효하지 않거나 만료된 리프레시 토큰입니다.");
        }
        return issueTokens(userId.get());
    }

    @Override
    @Transactional
    public void logout(RefreshTokenRequest request) {
        refreshTokenStore.delete(hash(request.refreshToken()));
    }

    private TokenResponse issueTokens(Long userId) {
        String accessToken = jwtProvider.createAccessToken(userId);
        String refreshToken = generateRefreshToken();
        refreshTokenStore.save(userId, hash(refreshToken),
            LocalDateTime.now().plus(jwtProperties.refreshTokenExpiration()));
        return new TokenResponse(accessToken, refreshToken);
    }

    private static String generateRefreshToken() {
        byte[] bytes = new byte[32];
        SECURE_RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private static String hash(String token) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is required by every JVM", e);
        }
    }
}
