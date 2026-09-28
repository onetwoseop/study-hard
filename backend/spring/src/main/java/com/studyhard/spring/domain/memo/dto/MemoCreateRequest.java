package com.studyhard.spring.domain.memo.dto;

import com.studyhard.spring.domain.memo.entity.MemoVisibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

public record MemoCreateRequest(
    @NotNull(message = "userId는 필수입니다.")
    Long userId,

    @NotBlank(message = "제목은 필수입니다.")
    String title,

    @NotBlank(message = "내용은 필수입니다.")
    String content,

    Integer studyTime,

    String subject,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate studyDate,

    @NotNull(message = "공개 범위는 필수입니다.")
    MemoVisibility visibility,

    List<MultipartFile> images
) {
}
