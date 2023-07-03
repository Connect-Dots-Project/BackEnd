## 📱 자유게시판

---

<br>
<br>
<br>

### 🚀 Goal

---

> 현재 행사중인 편의점의 목록들을 가져와 유저에게 제공한다.<br>
> 비회원은 접근이 불가하다.<br>
> GS25, CU, 7-Eleven 3개의 편의점 정보를 제공한다.<br>
> 현재 1+1, 2+1 행사 중인 제품을 알려준다.<br>
> 제품명, 가격 정보를 알려준다.<br>

<br>
<br>

### 🎯 기능 구현 로직

---

#### 권한

[//]: # (<br>)

| 기능            | 비회원 | 회원  |
|---------------|-----|-----|
| 편의점 정보 메인 페이지 | ❌   | ⭕️  |
| 행사 분류 보기      | ❌   | ⭕️  |
| 행사 상품 정보 보기   | ❌   | ⭕️  |

<br>
<br>

#### 🎈 시퀀스 다이어그램

---

<br>

#### 🎯 웹 크롤링

```mermaid
sequenceDiagram
    participant Python
    participant WebPage
    participant Spring
    participant DB
    autonumber

    Python ->> WebPage: Web Crawling
    WebPage ->> Python: Json Data
    Python ->> Spring: Json Data
    Spring ->> DB: Save Data
```

<br>
<br>

#### 🎯 서비스 이용

```mermaid
sequenceDiagram
    participant User
    participant CSV
    participant Application
    participant DB
    autonumber
    Note over User, DB: 회원만 접근 가능

    User ->>+ CSV: 메인 페이지
    CSV ->> CSV: Access Token 검사
    CSV ->> Application: Request
    Application ->> DB: Data 요청
    DB ->> Application: Data 취득
    Application ->> CSV: Response
    CSV ->> User: 편의점 행사 정보
```