package com.studyhard.spring.domain.memo.service;

import com.studyhard.spring.domain.memo.dto.MemoCreateRequest;
import com.studyhard.spring.domain.memo.dto.MemoResponse;
import com.studyhard.spring.domain.memo.entity.Memo;
import com.studyhard.spring.domain.memo.repository.MemoRepository;
import com.studyhard.spring.domain.user.entity.User;
import com.studyhard.spring.domain.user.repository.UserRepository;
import com.studyhard.spring.global.exception.BusinessException;
import com.studyhard.spring.global.s3.S3Uploader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;

    @Override
    @Transactional
    public MemoResponse createMemo(MemoCreateRequest request, List<MultipartFile> images) {
        User user = userRepository.findById(request.userId())
            .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."));

        List<String> imageUrls = s3Uploader.upload(images);

        Memo memo = Memo.builder()
            .user(user)
            .title(request.title())
            .content(request.content())
            .imageUrls(imageUrls)
            .studyTime(request.studyTime())
            .subject(request.subject())
            .studyDate(request.studyDate())
            .visibility(request.visibility())
            .build();

        return MemoResponse.from(memoRepository.save(memo));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemoResponse> getMemos(Long userId) {
        return memoRepository.findByUser_UserIdOrderByCreatedAtDesc(userId).stream()
            .map(MemoResponse::from)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MemoResponse getMemo(Long memoId) {
        Memo memo = memoRepository.findById(memoId)
            .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "존재하지 않는 메모입니다."));
        return MemoResponse.from(memo);
    }
}
