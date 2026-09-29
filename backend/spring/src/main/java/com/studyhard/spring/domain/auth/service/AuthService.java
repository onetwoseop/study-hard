package com.studyhard.spring.domain.auth.service;

import com.studyhard.spring.domain.auth.dto.LoginRequest;
import com.studyhard.spring.domain.auth.dto.RefreshTokenRequest;
import com.studyhard.spring.domain.auth.dto.SignupRequest;
import com.studyhard.spring.domain.auth.dto.TokenResponse;
import com.studyhard.spring.domain.user.dto.UserResponse;

public interface AuthService {

    UserResponse signup(SignupRequest request);

    TokenResponse login(LoginRequest request);

    TokenResponse reissue(RefreshTokenRequest request);

    void logout(RefreshTokenRequest request);
}
