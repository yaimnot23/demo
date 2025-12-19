# 이미지 업로드 구현 과정 상세 보고서

본 문서는 Spring Boot 게시판 프로젝트에 이미지 업로드 기능을 구현한 과정을 단계별로 기술합니다.

## 1. 개요 (Overview)

- **목표**: 게시글 등록(`Register`) 및 수정(`Modify`) 시 이미지를 첨부하고, 상세 페이지(`Detail`)에서 이를 확인하는 기능 구현.
- **저장 위치**: `src/main/resources/static/image` (프로젝트 내부 정적 리소스 폴더)

## 2. 구현 상세 내용 (Implementation Details)

### 2.1 데이터베이스 설계 (Database)

파일 정보를 저장하기 위한 `board_file` 테이블을 생성했습니다.

- `uuid`: 파일 중복 방지를 위한 고유 식별자 (PK)
- `save_dir`: 저장 경로 (웹 접근 경로)
- `file_name`: 원본 파일명
- `bno`: 게시글 번호 (FK)

### 2.2 설정 (Configuration)

`application.properties`에 파일 업로드 크기 제한을 설정했습니다.

```properties
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=20MB
```

### 2.3 백엔드 로직 (Backend)

#### 2.3.1 도메인 객체 (`FileVO`)

DB 테이블 구조에 맞춰 `FileVO` 클래스를 생성했습니다 (`uuid`, `saveDir`, `fileName`, `fileType`, `bno` 등).

#### 2.3.2 파일 처리 핸들러 (`FileHandler`)

- 실제 파일을 디스크(`static/image`)에 저장하는 역할을 담당합니다.
- `MultipartFile` 배열을 받아 UUID를 생성하고 파일명을 변경하여 저장한 뒤, `FileVO` 리스트를 반환합니다.

#### 2.3.3 데이터 접근 계층 (`FileDAO`, `fileMapper.xml`)

- `insertFile`: 파일 정보를 DB에 저장합니다.
- `getFileList`: 특정 게시글(`bno`)의 모든 첨부파일을 조회합니다.
- `delete/deleteAll`: 파일 삭제 기능을 제공합니다.

#### 2.3.4 서비스 & 컨트롤러 (`BoardService`, `BoardController`)

- **`BoardService`**: 파일 등록(`registerFile`) 및 조회(`getFileList`) 메서드를 추가했습니다.
- **`BoardController`**:
  - `register`/`modify`: `MultipartFile[] files` 파라미터를 받아 `FileHandler`에게 저장을 위임하고, 반환된 파일 정보를 DB에 등록합니다.
  - `detail`: 모델에 `fileList`를 담아 뷰로 전달합니다.

### 2.4 프론트엔드 (Frontend)

#### 2.4.1 글쓰기 페이지 (`register.html`)

- `<form>` 태그에 `enctype="multipart/form-data"` 속성을 추가하여 파일 전송이 가능하도록 했습니다.
- `<input type="file" multiple>` 태그를 추가하여 다중 파일 선택을 지원합니다.

#### 2.4.2 상세 페이지 (`detail.html`)

- **조회 모드**: `th:each`를 사용하여 첨부된 이미지들을 화면에 출력합니다.
- **수정 모드**: 평소에는 숨겨져 있던 파일 입력창(`fileInputDiv`)을 Javascript로 제어합니다.

#### 2.4.3 자바스크립트 (`boardModify.js`)

- [Modify] 버튼 클릭 시 수정 모드로 전환되며, 이때 파일 추가 버튼을 화면에 표시(`display: block`)합니다.

## 3. 결론 (Conclusion)

이제 사용자는 글을 작성하거나 수정할 때 이미지를 함께 업로드할 수 있으며, 업로드된 이미지는 즉시 상세 페이지에서 확인할 수 있습니다. 추가적으로 수정 시 기존 파일을 삭제하는 로직(AJAX 등)을 추후 고도화할 수 있습니다.
