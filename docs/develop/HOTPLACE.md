## 🔥 Hot Place

---

> 👩‍💻 담당자 : 박수민<br>
> 👉 [GitHub](https://github.com/330sum)


<br>
<br>

### 🚀 Goal

---

> 자유로운 글과 사진을 올릴 수 있는 게시판을 구현한다.<br>
> - #### 비회원/회원 공통
> - 작성된 글 전체 보기를 할 수 있다.
> - 작성된 글 검색 조회를 할 수 있다.
> - #### 회원
> - 글쓰기가 가능하다
> - 사진, 내용, 장소를 등록할 수 있다.
> - 글 수정, 삭제가 가능하다.
> - 지도 검색이 가능하다.
> - #### 비회원
> - 글쓰기가 불가하다.
> - 지도 검색이 불가하다.

<br>
<br>

### 🎯 기능 구현 로직

---

#### 접근 권한

| 기능       | 비회원 | 회원  |
|----------|-----|-----|
| 전체 글 보기  | ⭕️  | ⭕️  |
| 검색 조회    | ⭕️  | ⭕️  |
| 지도 조회    | ❌   | ⭕️  |
| 글 쓰기     | ❌   | ⭕️  |
| 글 수정, 삭제 | ❌   | ⭕️  |

<br>
<br>

#### 🎈 시퀀스 다이어그램

---

<br>

#### 🎯 전체 조회

```mermaid
sequenceDiagram
    participant User
    participant HotPlace
    participant Application
    participant DB
    autonumber
    Note over User, DB: 회원, 비회원 접근 가능
    User ->>+ HotPlace: 메인 페이지
    HotPlace ->>+ Application: Request
    Application ->>+ DB: 게시글 데이터 요청
    DB ->>- Application: 게시글 데이터 취득
    Application ->>- HotPlace: Response
    HotPlace ->>- User: 게시글 목록
```

<br>

#### 🎯 비회원 접근 차단

```mermaid
sequenceDiagram
    participant User
    participant HotPlace
    participant Application
    autonumber
    Note over User, Application: 비회원 접근 차단
    User ->>+ HotPlace: 글 삭제 버튼 클릭
    HotPlace ->> HotPlace: Access Token 검사
    HotPlace ->>+ Application: Request
    Application ->> Application: Jwt 유효성 검사
    Application ->>- HotPlace: 인증 실패 : Response 401
    HotPlace ->>- User: 로그인 유도
```

<br>

#### 🎯 글쓰기

```mermaid
sequenceDiagram
    participant User
    participant HotPlace
    participant KakaoMapAPI
    participant Application
    participant DB
    autonumber

    Note over User, DB: 회원만 가능
    User ->>+ HotPlace: 글 작성 버튼 클릭
    HotPlace ->> HotPlace: Access Token 검사
    HotPlace ->>+ KakaoMapAPI: Request Token
    KakaoMapAPI ->> KakaoMapAPI: Token 유효성 검사
    KakaoMapAPI ->>- HotPlace: Response 검색 결과
    HotPlace ->> HotPlace: 글쓰기 유효성 검사

    HotPlace ->>+ Application: Request
    Application ->> Application: Validated Request
    Application ->>+ DB: 글 저장 요청
    DB ->>- Application: 글 저장 결과
    Application ->>- HotPlace: Response Fail/Ok
    HotPlace ->>- User: 글 저장/실패
```

<br>

#### 🎯 지도 보기

```mermaid
sequenceDiagram
    participant User
    participant HotPlace
    participant KakaoMapAPI
    participant Application
    autonumber

    Note over User, DB: 회원만 접근 가능
    User ->>+ HotPlace: 지도 보기 클릭
    HotPlace ->> HotPlace: Access Token 검사
    HotPlace ->> KakaoMapAPI: Request Token
    KakaoMapAPI ->> KakaoMapAPI: Token 유효성 검사
    KakaoMapAPI ->> HotPlace: Response 지도 데이터
    HotPlace ->>+ Application: Request
    Application ->>+ DB: 게시글 데이터 요청
    DB ->>- Application: 게시글 데이터 취득
    Application ->>- HotPlace: Response
    HotPlace ->>- User: 게시글 목록
```

<br>

#### 🎯 글 삭제/수정하기

```mermaid
sequenceDiagram
    participant User
    participant HotPlace
    participant Application
    participant DB

    Note over User, DB: 회원만 접근 가능 (내가 쓴 글이 아니라면 요청 불가.)
    User ->> + HotPlace: 글 삭제 버튼 클릭
    HotPlace ->> User: 정말로 삭제 하시겠습니까? (Y/N)
    User ->> HotPlace: Yes
    HotPlace ->> + Application: Request
    Application ->> Application: Jwt 인증 확인
    Application ->> + DB: 게시글 요청
    DB ->> Application: 게시글 취득
    Application ->> Application: 본인 글 확인
    Application ->> DB: 삭제 요청
    DB ->>- Application: 삭제 결과
    Application ->> - HotPlace: Response
    HotPlace ->> - User: 본인 글 삭제 성공 여부
```