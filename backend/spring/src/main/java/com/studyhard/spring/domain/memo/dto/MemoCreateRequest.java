package com.studyhard.spring.domain.memo.dto;

import com.studyhard.spring.domain.memo.entity.MemoVisibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record MemoCreateRequest(
    @NotNull(message = "userId는 필수입니다.")
    Long userId,

    @NotBlank(message = "제목은 필수입니다.")
    String title,

    @NotBlank(message = "내용은 필수입니다.")
    String content,

    Integer studyTime,

    String subject,

    LocalDate studyDate,

    @NotNull(message = "공개 범위는 필수입니다.")
    MemoVisibility visibility
) {
}
