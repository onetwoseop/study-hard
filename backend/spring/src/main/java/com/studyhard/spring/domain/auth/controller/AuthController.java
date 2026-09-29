package com.studyhard.spring.domain.auth.controller;

import com.studyhard.spring.domain.auth.dto.LoginRequest;
import com.studyhard.spring.domain.auth.dto.RefreshTokenRequest;
import com.studyhard.spring.domain.auth.dto.SignupRequest;
import com.studyhard.spring.domain.auth.dto.TokenResponse;
import com.studyhard.spring.domain.auth.service.AuthService;
import com.studyhard.spring.domain.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "회원가입/로그인/토큰 API")
@SecurityRequirements
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "username/password/email/nickname으로 회원을 생성합니다.")
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody @Valid SignupRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(request));
    }

    @Operation(summary = "로그인", description = "Access Token(30분)과 Refresh Token(14일)을 발급합니다.")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "토큰 재발급", description = "Refresh Token으로 새 Access Token과 새 Refresh Token을 발급합니다. 사용한 Refresh Token은 폐기됩니다.")
    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@RequestBody @Valid RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.reissue(request));
    }

    @Operation(summary = "로그아웃", description = "Refresh Token을 폐기합니다. 발급된 Access Token은 만료 시까지 유효합니다.")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody @Valid RefreshTokenRequest request) {
        authService.logout(request);
        return ResponseEntity.noContent().build();
    }
}
