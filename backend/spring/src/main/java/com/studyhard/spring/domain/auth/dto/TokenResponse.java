package com.studyhard.spring.domain.auth.dto;

public record TokenResponse(
    String accessToken,
    String refreshToken
) {
}
