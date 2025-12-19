# 회원가입(Registration) 동작 흐름 상세 분석

이 문서는 사용자가 회원가입을 진행할 때, **화면(View)에서부터 데이터베이스(DB)까지** 데이터가 어떻게 이동하고 처리되는지 상세한 흐름(Flow)을 설명합니다.

---

## 📌 전체 흐름 요약

1.  **View (`register.html`)**: 사용자가 정보 입력 후 전송 (POST 요청)
2.  **Controller (`UserController`)**: 요청을 받아 데이터를 객체(`UserVO`)로 변환
3.  **Service (`UserServiceImpl`)**: 비밀번호 암호화 및 비즈니스 로직 수행
4.  **DAO (`UserDAO`)**: DB 작업을 위한 인터페이스 호출
5.  **Mapper (`userMapper.xml`)**: 실제 SQL 실행
6.  **DB (`MySQL`)**: `member` 테이블과 `auth` 테이블에 데이터 저장

---

## 🔍 단계별 상세 분석

### 1. View (화면)

- **파일**: `src/main/resources/templates/user/register.html`
- **역할**: 사용자 입력을 받는 곳입니다.
- **동작**:
  ```html
  <form action="/user/register" method="post">
    <input name="email" ... />
    <input name="pwd" ... />
    <input name="nickName" ... />
    <button type="submit">Register</button>
  </form>
  ```
  - 사용자가 `Register` 버튼을 누르면 `<form>` 태그의 `action` 속성에 적힌 **`/user/register`** 주소로 **`POST`** 방식의 요청을 보냅니다.
  - 이때 `name` 속성인 `email`, `pwd`, `nickName`이 데이터 꾸러미(Parameter)가 되어 날아갑니다.

### 2. Controller (컨트롤러)

- **파일**: `src/main/java/com/example/demo/controller/UserController.java`
- **역할**: 웹 요청을 받는 창구(데스크)입니다.
- **코드**:
  ```java
  @PostMapping("/register") // 1. POST 요청을 받음
  public String register(UserVO userVO) { // 2. 입력값들을 UserVO 객체에 자동으로 담음 (파라미터 매핑)
      log.info(">>> register: {}", userVO);
      int isOk = userService.register(userVO); // 3. Service에게 "회원가입 처리해줘"라고 시킴
      return "index"; // 4. 처리가 끝나면 메인 화면(index)으로 보냄
  }
  ```
  - 스프링이 알아서 화면에서 보낸 `email`, `pwd`, `nickName`을 `UserVO` 객체의 필드에 쏙쏙 넣어줍니다.

### 3. Service (서비스)

- **파일**: `src/main/java/com/example/demo/service/UserServiceImpl.java`
- **역할**: 실제 업무(비즈니스 로직)를 처리하는 실무자입니다. **가장 중요한 곳**입니다.
- **코드**:

  ```java
  @Override
  public int register(UserVO userVO) {
      // 1. 비밀번호 암호화 (보안 필수!)
      // 사용자가 입력한 "1111"을 "$2a$10$..." 같은 알 수 없는 문자로 바꿈
      userVO.setPwd(passwordEncoder.encode(userVO.getPwd()));

      // 2. 회원 정보 저장 시도 (DAO 호출)
      int isUserOk = userDAO.insertUser(userVO);

      // 3. 권한 부여 (DAO 호출)
      // 가입하는 사람은 기본적으로 'ROLE_USER'라는 명찰(권한)을 받음
      return userDAO.insertAuth(userVO.getEmail(), "ROLE_USER");
  }
  ```

  - **핵심 포인트**: 여기서 `PasswordEncoder`를 써서 비밀번호를 암호화하지 않으면, 보안상 큰 문제가 생기며 기능적으로도 로그인이 되지 않습니다(스프링 시큐리티는 암호화된 비밀번호만 취급하기 때문).

### 4. DAO (데이터 접근 객체)

- **파일**: `src/main/java/com/example/demo/repository/UserDAO.java`
- **역할**: Service가 DB에 일을 시킬 때 사용하는 명세서(인터페이스)입니다.
- **코드**:
  ```java
  int insertUser(UserVO userVO); // 회원 넣기
  int insertAuth(String email, String auth); // 권한 넣기
  ```
  - 실제 구현 코드는 없고, "이런 기능을 `xml`에 있는 SQL과 연결해줘"라고 약속만 정의합니다.

### 5. Mapper (SQL 매퍼)

- **파일**: `src/main/resources/mappers/userMapper.xml`
- **역할**: 실제 DB에 날릴 SQL 쿼리문이 들어있는 곳입니다.
- **코드**:

  ```xml
  <!-- 회원 정보 저장 -->
  <insert id="insertUser">
      INSERT INTO member (email, pwd, nick_name)
      VALUES (#{email}, #{pwd}, #{nickName})
  </insert>

  <!-- 권한 저장 -->
  <insert id="insertAuth">
      INSERT INTO auth (email, auth)
      VALUES (#{email}, #{auth})
  </insert>
  ```

  - `#{email}` 등의 변수 자리에 실제 데이터가 채워져서 DB로 전송됩니다.

### 6. Database (DB)

- **결과**:
  - `member` 테이블: `test@test.com`, `(암호화된 비번)`, `테스터` 행 추가됨.
  - `auth` 테이블: `test@test.com`, `ROLE_USER` 행 추가됨.

---

## 💡 Role(권한)은 왜 Service에서 따로 넣나요?

화면(`register.html`)에서는 아이디/비번만 입력했지, "나는 관리자야!"라고 입력하지 않았습니다.
그래서 **Service(백엔드 로직)**에서 "신규 가입자는 무조건 일반 유저(`ROLE_USER`)다"라고 규칙을 정해서 넣어주는 것입니다. 만약 관리자 가입 페이지가 따로 있다면, 거기서는 Service가 `ROLE_ADMIN`을 넣어주게 되겠죠?
