## 📱 LiveChat

---

<br>
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

| 기능       | 비회원 | 회원  |
|----------|-----|-----|
| 채팅 목록 보기 | ❌️  | ⭕️  |
| 채팅방 만들기  | ❌   | ⭕️  |
| 채팅하기     | ❌   | ⭕️  |

<br>
<br>

#### 🎈 시퀀스 다이어그램

---

<br>

#### 🎯 전체 조회

```mermaid
sequenceDiagram
    participant User
    participant LiveChat
    participant Application
    participant DB
    autonumber
    Note over User, DB: 회원만 접근 가능
    User ->>+ LiveChat: 메인 페이지
    LiveChat ->>+ Application: Request
    Application ->>+ DB: 채팅 목록 데이터 요청
    DB ->>- Application: 채팅 목록 데이터 취득
    Application ->>- LiveChat: Response
    LiveChat ->>- User: 채팅방 전체 조회
```

<br>
<br>

#### 🎯 채팅방 만들기

```mermaid
sequenceDiagram
    participant User
    participant LiveChat
    participant Application
    participant DB
    autonumber
    Note over User, DB: 회원만 접근 가능하며 1인당 1개의 채팅방을 생성할 수 있다.

    User ->>+ LiveChat: 글쓰기 클릭
    LiveChat ->> LiveChat: Access Token 검사
    LiveChat ->>+ Application: Request
    Application ->>+ DB: 채팅방 생성이 가능한지 확인
    DB ->>- Application: 채팅방 생성 불가능/가능
    Application ->>- LiveChat: Request Fail/Ok
    LiveChat ->>- User: 채팅방 미생성/생성
```

<br>
<br>

#### 🎯 채팅하기 TODO: 해야함

```mermaid
sequenceDiagram
    participant User
    participant LiveChat
    participant Application
    autonumber
    Note over User, Application: 회원만 접근 가능하며 1인당 1개의 채팅방을 생성할 수 있다.

    User ->>+ LiveChat: 채팅방 클릭
    LiveChat ->>+ Application: 구독 Request
    Application ->> Application: 유저는 해당 방을 구독하게 됨
    Application ->> LiveChat: 구독 Response
    LiveChat ->> User: 입장 메시지
    User ->> LiveChat: 채팅 내용 입력
    LiveChat ->> Application: 내용 Request
    Application ->> Application: 구독하고 있는 유저들에게 메시지를 전달함
    Application ->> LiveChat: 내용 Response
    LiveChat ->> User: 채팅 내용
```