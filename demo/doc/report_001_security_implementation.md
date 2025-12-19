# Spring Security 및 회원 인증 구현 작업 내역

## 1. 개요

Spring Security를 적용하여 로그인 및 권한 관리 시스템을 구축하기 위한 기본 설정을 완료했습니다.
기존 DB 스키마(`member`, `auth`)를 활용하여 인증 로직을 구현했습니다.

## 2. 생성 및 수정된 파일 목록

### 설정 (Configuration)

- **`src/main/java/com/example/demo/config/SecurityConfig.java`**
  - `SecurityFilterChain`: URL별 접근 권한 설정 (로그인, 리소스 등 허용), Form 로그인/로그아웃 설정.
  - `PasswordEncoder`: 비밀번호 암호화 빈 등록 (`BCrypt` 등 위임).
  - `AuthenticationManager`: 인증 관리자 빈 등록.
  - `UserDetailsService`: `CustomUserService`를 빈으로 등록하여 DB 인증 연결.

### 도메인 (Domain)

- **`src/main/java/com/example/demo/domain/UserVO.java`**
  - 회원 정보 객체 (`email`, `pwd`, `nickName` 등). `member` 테이블과 매핑.
- **`src/main/java/com/example/demo/domain/AuthVO.java`**
  - 권한 정보 객체 (`email`, `auth`). `auth` 테이블과 매핑.

### 데이터 접근 (Repository/Mapper)

- **`src/main/java/com/example/demo/repository/UserDAO.java`**
  - 회원 정보 조회 및 가입을 위한 인터페이스.
- **`src/main/resources/mappers/userMapper.xml`**
  - 실제 SQL 쿼리 정의.
  - `member` 테이블과 `auth` 테이블을 조인하여 회원 정보와 권한을 한 번에 조회.

### 시큐리티 (Security)

- **`src/main/java/com/example/demo/security/AuthMember.java`**
  - Spring Security가 사용하는 `User` 클래스를 상속받은 커스텀 유저 클래스.
  - `UserVO`를 내부에 포함하여 컨트롤러 등에서 실제 회원 정보에 접근 가능하도록 함.
- **`src/main/java/com/example/demo/security/CustomUserService.java`**
  - `UserDetailsService` 인터페이스 구현체.
  - `UserDAO`를 통해 DB에서 회원 정보를 가져와 `AuthMember` 객체로 반환.

### 기타

- **`src/main/resources/user_schema.sql`**
  - (참고용) 회원가입 및 권한 테이블 생성 쿼리문.

## 3. 구현 상세 내용

1.  **DB 연동**: 사용자가 제공한 스키마(`member`, `auth`)에 맞춰 `Mapper`를 수정하여 연동 완료.
2.  **빈 등록 최적화**: `CustomUserService`에서 `@Service`를 제거하고, `SecurityConfig`에서 명시적으로 `@Bean`으로 등록하여 의존성 관리를 명확하게 함.
3.  **URL 보안**: `/user/list`는 ADMIN 권한 필요, 정적 리소스 및 로그인/가입 페이지는 전체 허용으로 설정.

## 4. 향후 계획 (Next Steps)

- 로그인 페이지(`login.html`) 생성 및 컨트롤러 연결.
- 회원가입(`join`) 로직 구현.
- 화면에서 로그인 여부에 따른 UI 처리 (`sec:authorize` 태그 활용).
