package com.studyhard.spring.domain.memo.dto;

import com.studyhard.spring.domain.memo.entity.Memo;
import com.studyhard.spring.domain.memo.entity.MemoVisibility;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record MemoResponse(
    Long memoId,
    Long userId,
    String title,
    String content,
    List<String> imageUrls,
    Integer studyTime,
    String subject,
    LocalDate studyDate,
    MemoVisibility visibility,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static MemoResponse from(Memo memo) {
        return new MemoResponse(
            memo.getMemoId(),
            memo.getUser().getUserId(),
            memo.getTitle(),
            memo.getContent(),
            memo.getImageUrls(),
            memo.getStudyTime(),
            memo.getSubject(),
            memo.getStudyDate(),
            memo.getVisibility(),
            memo.getCreatedAt(),
            memo.getUpdatedAt()
        );
    }
}
