## 📱 자유게시판

---


> 👩‍💻 담당자 : 이기덕<br>
> 👉 [GitHub](https://github.com/lkdcode)


<br>
<br>

### 🚀 Goal

---

> 자유로운 글과 사진을 올릴 수 있는 게시판을 구현한다.<br>
> 전체 게시글의 내용을 간략하게 보여줄 수 있는 페이지가 있다<br>
> 상세보기 페이지에서 댓글을 작성할 수 있으며<br>
> 글의 모든 내용과 해당 글의 모든 댓글을 볼 수 있다.<br>

<br>
<br>

### 🎯 기능 구현 로직

---

#### 권한

<br>

| 기능      | 비회원 | 회원  |
|---------|-----|-----|
| 전체 글 보기 | ⭕️  | ⭕️  |
| 글 쓰기    | ❌   | ⭕️  |
| 글 상세 보기 | ❌   | ⭕️  |
| 댓글 쓰기   | ❌   | ⭕️  |
| 글 삭제    | ❌   | ⭕️  |

<br>
<br>

#### 🎈 시퀀스 다이어그램

---

<br>

#### 🎯 전체 조회

```mermaid
sequenceDiagram
    participant User
    participant 자유게시판
    participant Application
    participant DB
    autonumber
    Note over User, DB: 회원, 비회원 접근 가능
    User ->>+ 자유게시판: 랜딩 페이지
    자유게시판 ->>+ Application: Request
    Application ->>+ DB: 게시글 데이터 요청
    DB ->>- Application: 게시글 데이터 취득
    Application ->>- 자유게시판: Response
    자유게시판 ->>- User: 게시글 목록
```

<br>

#### 🎯 비회원 접근 차단

```mermaid
sequenceDiagram
    participant User
    participant 자유게시판
    participant Application
    autonumber
    Note over User, Application: 비회원 접근
    User ->>+ 자유게시판: 글 삭제 버튼 클릭
    자유게시판 ->> 자유게시판: Access Token 검사
    자유게시판 ->>+ Application: Request
    Application ->> Application: Jwt 유효성 검사
    Application ->>- 자유게시판: 인증 실패 : Response 401
    자유게시판 ->>- User: 로그인 유도

```

<br>

#### 🎯 글 상세보기

```mermaid
sequenceDiagram
    participant User
    participant 자유게시판
    participant Application
    participant DB
    autonumber

    Note over User, DB: 비회원 접근 불가
    User ->>+ 자유게시판: 게시글 클릭
    자유게시판 ->> 자유게시판: Access Token 검사
    자유게시판 ->> User: 실패시 접근 불가
    자유게시판 ->>+ Application: Access Token이 유효하다면<br>Request
    Application ->> Application: Jwt 유효성 검사
    Application ->>- 자유게시판: 인증 실패 : Reseponse 401
    자유게시판 ->>- User: 로그인 유도

    Note over User, DB: 회원 접근 가능
    User ->>+ 자유게시판: 글 쓰기 버튼 클릭
    자유게시판 ->>+ Application: Request
    Application ->> Application: Jwt 유효성 검사
    Application ->>+ DB: 게시글 데이터 요청
    DB ->> - Application: 게시글 데이터 취득
    Application ->>- 자유게시판: Response
    자유게시판 ->>- User: 게시글 목록
```

<br>

#### 🎯 글 삭제하기

```mermaid
sequenceDiagram
    participant User
    participant 자유게시판
    participant Application
    participant DB
    autonumber
    Note over User, DB: 회원만 접근 가능 (내가 쓴 글이 아니라면)
    User ->>+ 자유게시판: 글 삭제 버튼 클릭
    자유게시판 ->> User: 정말로 삭제 하시겠습니까? (Y/N)
    User ->> 자유게시판: Yes
    자유게시판 ->>+ Application: Request
    Application ->> Application: Jwt 유효성 검사
    Application ->> Application: 본인 글 확인
    Application ->>- 자유게시판: Response False
    자유게시판 ->>- User: 삭제 불가

    Note over User, DB: 회원만 접근 가능 (내가 쓴 글이라면)
    User ->>+ 자유게시판: 글 삭제 버튼 클릭
    자유게시판 ->> User: 정말로 삭제 하시겠습니까? (Y/N)
    User ->> 자유게시판: Yes
    자유게시판 ->>+ Application: Request
    Application ->> Application: Jwt 유효성 검사
    Application ->> Application: 본인 글 확인
    Application ->>+ DB: 삭제 요청
    DB ->>- Application: 삭제 성공
    Application ->>- 자유게시판: Response
    자유게시판 ->>- User: 삭제 성공
```

<br>
<br>