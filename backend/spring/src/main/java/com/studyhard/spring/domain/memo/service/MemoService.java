package com.studyhard.spring.domain.memo.service;

import com.studyhard.spring.domain.memo.dto.MemoCreateRequest;
import com.studyhard.spring.domain.memo.dto.MemoResponse;
import java.util.List;

public interface MemoService {

    MemoResponse createMemo(MemoCreateRequest request);

    List<MemoResponse> getMemos(Long userId);

    MemoResponse getMemo(Long memoId);
}
