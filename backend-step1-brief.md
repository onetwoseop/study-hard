# 백엔드 1단계 작업 브리핑 — User(최소) + Memo 도메인, MySQL/S3 연동 확인

Claude Code에게: 아래 내용을 읽고 작업을 진행해줘. 애매한 부분이 있으면 코드부터 짜지 말고 먼저 질문해줘.

---

## 1. 프로젝트 배경

- "studyHard"라는 개인 프로젝트: 공부 기록을 작성하고 AI로 요약/문제를 생성해주는 웹 서비스
- 전체 구조:
  ```
  study-hard/
  ├── frontend/          (Next.js, 랜딩 페이지 이미 완성되어 AWS EC2에 배포 테스트 완료)
  ├── backend/
  │   ├── spring/        (이번 작업 대상 — Spring Boot)
  │   └── fastapi/        (아직 미착수, 외부 AI API 호출 전담 예정)
  └── ...
  ```
- 인프라: AWS EC2(Ubuntu, t3.micro, 시드니 리전 ap-southeast-2)에 배포 예정. 지금은 로컬 개발 단계.
- **로컬 개발 환경**: IntelliJ에서 실제 코드 작성/빌드/디버깅. 이 브리핑을 읽는 Claude Code는 VSCode에서 Remote 또는 로컬로 작업 중.

## 2. 기술 스택 (Spring 프로젝트 기준)

- Java 17, Gradle (Groovy)
- Spring Boot 3.x
- Spring Web, Spring Data JPA
- MySQL (드라이버: `com.mysql.cj.jdbc.Driver`)
- Spring Security + OAuth2 Client (카카오, 네이버 — 이번 작업 범위 아님, 추후)
- Spring Data Redis (추후 도입 예정, 이번 작업 범위 아님)
- Lombok
- Validation (`@NotBlank` 등)
- springdoc-openapi (Swagger UI, `/swagger-ui/index.html`에서 확인)
- AWS S3 연동: `io.awspring.cloud:spring-cloud-aws-starter-s3` (S3Template 사용)
- 환경변수 관리: `.env` 파일 + `me.paulschwarz:spring-dotenv` 라이브러리로 로드 (`.env`는 `.gitignore`에 포함, git에 올리지 않음)

## 3. DB / S3 설정값

- MySQL DB 이름: `studyhard`, 문자셋 `utf8mb4`
- 전용 DB 유저 별도 생성해서 사용 (root 아님)
- S3 버킷명: `study-hard-oneseop-bucket`
- S3/EC2 리전: `ap-southeast-2` (시드니) — 반드시 이 리전으로 설정
- `.env`에 담길 값 (예시, 실제 값은 이미 로컬에 존재한다고 가정하고 참조만 할 것):
  ```
  DB_PASSWORD=...
  AWS_ACCESS_KEY=...
  AWS_SECRET_KEY=...
  ```
- `application.yml`은 이 값들을 `${DB_PASSWORD}`, `${AWS_ACCESS_KEY}`, `${AWS_SECRET_KEY}` 형태로 참조

## 4. 패키지 구조 컨벤션 — Domain 기준

Layer 기준(controller/service/repository 최상위 분리)이 아니라, **도메인별로 폴더를 나누고 그 안에 entity/repository/service/controller/dto를 둠.**

```
src/main/java/com/studyhard/backend
├── StudyHardApplication.java
├── global/
│   ├── config/          (SecurityConfig, SwaggerConfig 등)
│   ├── exception/
│   ├── common/
│   │   └── BaseTimeEntity.java   (created_at/updated_at 공통 상속용, @MappedSuperclass)
│   └── s3/
│       └── S3Uploader.java       (S3Template 사용, MultipartFile 업로드 → URL 반환)
└── domain/
    ├── user/
    │   ├── entity/, repository/, service/, controller/, dto/
    └── memo/
        ├── entity/, repository/, service/, controller/, dto/
```

## 5. 코드 컨벤션

- Entity: `@Getter`만 사용, `@Setter` 남발하지 않음. 생성은 `@Builder`로, 수정이 필요하면 명시적 업데이트 메서드로.
- `created_at`/`updated_at`은 `BaseTimeEntity` 상속으로 공통 처리 (`@CreatedDate`, `@LastModifiedDate`, `@EnableJpaAuditing` 필요)
- Service는 인터페이스 + 구현체(Impl)로 분리하는 패턴 유지 (예: `MemoService` 인터페이스 + `MemoServiceImpl`)
- DTO는 Java `record` 사용 (Request/Response 분리)
- Controller에 Swagger 어노테이션(`@Tag`, `@Operation`) 포함
- 테이블명은 단수형이 기본이나, `user`는 SQL 예약어라 예외적으로 **`users`**로 사용
- FK 컬럼명은 `<참조테이블>_id` 형태 유지 (예: `user_id`)

## 6. 이번 작업 범위 — 2개 도메인만 구현

### 6-1. User 도메인 (최소 버전 — 인증 로직 없음)

**목적**: Memo 테스트 시 FK로 쓸 유저를 만들기 위한 최소 기능. OAuth2/JWT 로그인은 이번 범위 아님.

**Entity 컬럼** (`users` 테이블):
- `user_id` (PK)
- `username`
- `password_hash` (지금은 평문 저장 없이 최소한 BCrypt 정도는 적용 권장하되, 복잡한 인증 로직은 넣지 않음)
- `email`
- `nickname`
- `oauth_provider` (enum: LOCAL, KAKAO, NAVER — 컬럼만 만들어두고 지금은 LOCAL만 사용)
- `oauth_id` (nullable, 지금은 사용 안 함)
- `created_at`, `updated_at` (BaseTimeEntity 상속)

**API**: 회원가입 API 하나만 구현 (`POST /api/users`). 로그인 API는 이번 범위 아님 — 인증 없이 `userId`를 직접 파라미터로 넘기는 방식으로 이후 Memo API를 테스트할 것이므로, 로그인 절차 자체가 필요 없음.

### 6-2. Memo 도메인 (핵심 — 이걸로 MySQL + S3 연동을 동시에 검증)

**Entity 컬럼** (`memo` 테이블):
- `memo_id` (PK)
- `user_id` (FK)
- `title`
- `content`
- `image_urls` (JSON 배열로 저장, S3에 업로드된 이미지들의 URL 목록)
- `study_time` (분 단위, Integer)
- `subject` (문자열)
- `study_date` (LocalDate)
- `visibility` (enum: PRIVATE, FRIENDS)
- `created_at`, `updated_at` (BaseTimeEntity 상속)

**API**:
- `POST /api/memos` — 메모 생성. **이미지 파일(MultipartFile, 여러 개 가능)을 같이 받아서, S3Uploader로 업로드 → 반환된 URL들을 image_urls에 저장 → memo를 MySQL에 저장**하는 흐름까지 한 번에 처리
- `GET /api/memos` — 특정 유저의 메모 목록 조회 (userId를 요청 파라미터로 받음, 인증 없음)
- `GET /api/memos/{memoId}` — 메모 단건 조회

## 7. 이번 작업에서 하지 않을 것 (명확히 제외)

- OAuth2(카카오/네이버) 로그인 구현
- JWT Access/Refresh Token 발급 로직
- Redis 연동
- summary, quiz, friendship, comment, goal, badge, ai_usage, reference 등 다른 도메인 (다음 단계에서 순차적으로 진행 예정)
- 로그인 세션/인증 검증 로직 (`@AuthenticationPrincipal` 등) — 지금은 요청에 `userId`를 직접 넘기는 방식으로 임시 처리

## 8. 완료 후 확인 절차 (Claude Code가 안내해줄 것)

1. `POST /api/users`로 유저 하나 생성
2. `POST /api/memos`에 텍스트 필드 + 이미지 파일 첨부해서 요청 (Swagger UI에서 실행)
3. 응답의 `imageUrls`가 실제 S3에 업로드된 URL인지 확인 (브라우저에 붙여넣어 이미지 확인)
4. MySQL(Workbench 등)에서 `users`, `memo` 테이블에 실제 row가 생겼는지 확인

이 절차까지 통과하면 MySQL 연동과 S3 연동이 동시에 검증된 것으로 간주함.

---

**Claude Code에게 요청**: 위 범위(User 최소 버전 + Memo 도메인)만 구현하고, 7번 항목은 이번에 손대지 마. 완료되면 위 8번 확인 절차를 어떻게 진행하면 되는지 단계별로 안내해줘.
