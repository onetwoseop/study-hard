package com.studyhard.spring.domain.memo.entity;

import com.studyhard.spring.domain.user.entity.User;
import com.studyhard.spring.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "memo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Memo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_id")
    private Long memoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Convert(converter = ImageUrlsConverter.class)
    @Column(name = "image_urls", columnDefinition = "json")
    private List<String> imageUrls;

    @Column(name = "study_time")
    private Integer studyTime;

    private String subject;

    @Column(name = "study_date")
    private LocalDate studyDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemoVisibility visibility;

    @Builder
    private Memo(User user, String title, String content, List<String> imageUrls,
                 Integer studyTime, String subject, LocalDate studyDate, MemoVisibility visibility) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.imageUrls = imageUrls;
        this.studyTime = studyTime;
        this.subject = subject;
        this.studyDate = studyDate;
        this.visibility = visibility;
    }
}
