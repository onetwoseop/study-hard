package com.studyhard.spring.domain.memo.repository;

import com.studyhard.spring.domain.memo.entity.Memo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    List<Memo> findByUser_UserIdOrderByCreatedAtDesc(Long userId);
}
