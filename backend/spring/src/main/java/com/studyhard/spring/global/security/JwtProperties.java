package com.studyhard.spring.global.security;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @param secret base64-encoded HMAC key of at least 256 bits
 */
@ConfigurationProperties("jwt")
public record JwtProperties(
    String secret,
    Duration accessTokenExpiration,
    Duration refreshTokenExpiration
) {
}
