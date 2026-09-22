package com.studyhard.spring.domain.user.dto;

import com.studyhard.spring.domain.user.entity.OauthProvider;
import com.studyhard.spring.domain.user.entity.User;
import java.time.LocalDateTime;

public record UserResponse(
    Long userId,
    String username,
    String email,
    String nickname,
    OauthProvider oauthProvider,
    LocalDateTime createdAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
            user.getUserId(),
            user.getUsername(),
            user.getEmail(),
            user.getNickname(),
            user.getOauthProvider(),
            user.getCreatedAt()
        );
    }
}
