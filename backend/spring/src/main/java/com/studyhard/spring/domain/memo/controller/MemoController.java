package com.studyhard.spring.domain.memo.controller;

import com.studyhard.spring.domain.memo.dto.MemoCreateRequest;
import com.studyhard.spring.domain.memo.dto.MemoResponse;
import com.studyhard.spring.domain.memo.service.MemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Memo", description = "공부 기록 API")
@RestController
@RequestMapping("/api/memos")
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @Operation(summary = "메모 생성", description = "메모 정보(폼 필드)와 이미지 파일(images)을 함께 업로드합니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MemoResponse> createMemo(
        @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
        @ModelAttribute @Valid MemoCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memoService.createMemo(userId, request));
    }

    @Operation(summary = "내 메모 목록 조회", description = "로그인한 사용자의 메모 목록을 최신순으로 조회합니다.")
    @GetMapping
    public ResponseEntity<List<MemoResponse>> getMemos(@Parameter(hidden = true) @AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(memoService.getMemos(userId));
    }

    @Operation(summary = "메모 단건 조회", description = "본인의 메모만 조회할 수 있습니다.")
    @GetMapping("/{memoId}")
    public ResponseEntity<MemoResponse> getMemo(
        @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
        @PathVariable Long memoId
    ) {
        return ResponseEntity.ok(memoService.getMemo(userId, memoId));
    }
}
