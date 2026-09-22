package com.studyhard.spring.domain.user.service;

import com.studyhard.spring.domain.user.dto.UserResponse;
import com.studyhard.spring.domain.user.dto.UserSignupRequest;
import com.studyhard.spring.domain.user.entity.OauthProvider;
import com.studyhard.spring.domain.user.entity.User;
import com.studyhard.spring.domain.user.repository.UserRepository;
import com.studyhard.spring.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse signup(UserSignupRequest request) {
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
}
