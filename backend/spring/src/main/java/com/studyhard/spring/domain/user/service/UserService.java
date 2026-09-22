package com.studyhard.spring.domain.user.service;

import com.studyhard.spring.domain.user.dto.UserResponse;
import com.studyhard.spring.domain.user.dto.UserSignupRequest;

public interface UserService {

    UserResponse signup(UserSignupRequest request);
}
