## 🎧 플레이 리스트

---

<br>
<br>
<br>

### 🚀 Goal

---

> Spotify 에서 추천하는 음악 플레이 리스트들을 가져와 유저들에게 제공해준다.<br>
> 비회원은 접근이 불가하다.<br>
> 해당 트랙의 주제와 트랙안에 추천 음악 리스트들이 있다.<br>
> 앨범 커버와 제목, 가수를 보여준다.<br>
> 미리듣기가 가능한 경우 제공해준다.<br>

<br>
<br>

### 🎯 기능 구현 로직

---

#### 권한

<br>

| 기능             | 비회원 | 회원  |
|----------------|-----|-----|
| 플레이 리스트 메인 페이지 | ❌   | ⭕️  |
| 추천 리스트 보기      | ❌   | ⭕️  |
| 미리 듣기          | ❌   | ⭕️  |

<br>
<br>

#### 🎈 시퀀스 다이어그램

---

<br>

#### 🎯 SpotifyAPI

```mermaid
sequenceDiagram
    participant BackEnd
    participant Spotify
    participant Developer
    participant DB
    autonumber
    BackEnd ->> Spotify: Request authorization to accessToken
    Spotify ->> Developer: Login authorization
    Developer ->> BackEnd: Request access and Refresh Token
    BackEnd ->> Spotify: Request Token
    Spotify ->> BackEnd: Response JSON object
    BackEnd ->> DB: Save
```

<br>
<br>

#### 🎯 서비스 이용

```mermaid
sequenceDiagram
    participant User
    participant PlayList
    participant BackEnd
    participant DB
    autonumber
    Note over User, DB: 회원만 접근 가능
    User ->>+ PlayList: 메인 페이지
    PlayList ->> PlayList: Access Token 검사
    PlayList ->>+ BackEnd: Request
    BackEnd ->>+ DB: 음악 데이터 요청
    DB ->>- BackEnd: 음악 데이터 취득
    BackEnd ->>- PlayList: Response
    PlayList ->>- User: 음악 목록
```