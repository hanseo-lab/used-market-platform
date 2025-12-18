# 🛒 Spring Boot + React 중고 거래 플랫폼

Spring Boot 백엔드와 React 프론트엔드를 연동한 풀스택 중고 물품 거래 플랫폼입니다.  
사용자는 물품을 등록(이미지 포함)하고, 댓글을 남기거나 찜(Wishlist)을 할 수 있으며, 판매 상태를 관리할 수 있습니다.

---

## 📋 목차

- [주요 기능](#-주요-기능)
- [기술 스택](#-기술-스택)
- [프로젝트 구조](#-프로젝트-구조)
- [설치 및 실행](#-설치-및-실행)
- [API 명세 및 상태 관리](#-api-명세-및-상태-관리)
- [페이지 구성](#-페이지-구성)
- [트러블 슈팅](#-트러블-슈팅)

---

## 🎯 주요 기능

### 1. 회원 관리 (Member)

- ✅ **회원가입**: 이메일, 비밀번호, 이름, 전화번호, 주소 등록 (중복 이메일 체크)
- ✅ **로그인/로그아웃**: DB 기반 인증 및 세션 관리 (LocalStorage에 로그인 상태 유지)
- ✅ **마이페이지**: 내 정보(주소, 비밀번호) 수정
- ✅ **회원 탈퇴**: 비밀번호 재확인(검증) 후 계정 및 연관 데이터(작성글, 댓글, 찜) 일괄 삭제

### 2. 물품 거래 (Product)

- ✅ **물품 등록**: 제목, 가격, 설명, 카테고리, 이미지 파일 업로드(Multipart)
- ✅ **물품 수정/삭제**: 작성자 본인만 가능 (이미지 교체 지원)
- ✅ **판매 상태 관리**: 판매중(FOR_SALE) / 예약중(RESERVED) / 판매완료(SOLD_OUT) 상태 변경 (Enum 관리)
- ✅ **목록 조회**: 최신순 정렬, 카테고리별 필터링, 키워드 검색
- ✅ **상세 조회**: 조회수 증가, 판매자 정보 및 상태 확인

### 3. 커뮤니티 & 편의 기능

- ✅ **댓글 시스템**: 상품에 대한 문의 댓글 작성/조회 (작성자 표시)
- ✅ **찜하기(Wishlist)**: 관심 상품 등록/해제 및 마이페이지에서 모아보기
- ✅ **유효성 검사**: 폼 입력값 검증 (이메일 형식, 필수 값 등)

---

## 🛠 기술 스택

### Frontend

- **Framework**: React 18, Vite
- **State Management**: Zustand (API 비동기 통신 및 전역 상태 관리)
- **Networking**: Axios (REST API 호출)
- **Styling**: Styled-components
- **Routing**: React Router DOM v6

### Backend

- **Framework**: Spring Boot 3.x
- **Language**: Java 17
- **Database**: H2 (Dev) / MySQL (Prod 권장)
- **Persistence**: Spring Data JPA (Hibernate)
- **Build Tool**: Gradle

---

## 📁 프로젝트 구조

```
root/
├── spring-board-project/      # Backend (Spring Boot)
│   ├── src/main/java/com/kh/board/
│   │   ├── controller/        # REST Controller
│   │   ├── service/           # 비즈니스 로직
│   │   ├── repository/        # JPA/EntityManager Repository
│   │   ├── entity/            # DB Entity (JPA)
│   │   ├── dto/               # Request/Response DTO
│   │   └── config/            # WebConfig (CORS, ResourceHandler)
│   └── resources/
│       └── application.yaml   # DB 및 서버 설정
│
└── src/                       # Frontend (React)
    ├── store/                 # Zustand Stores (Axios 호출)
    │   ├── authStore.js       # 로그인, 회원가입, 탈퇴 액션
    │   └── itemStore.js       # 상품 CRUD 액션
    ├── components/            # 공통 컴포넌트 (Layout, ProtectedRoute)
    ├── pages/                 # 페이지 컴포넌트 (Login, ItemDetail 등)
    ├── styles/                # Styled-components 파일
    └── routes/                # 라우터 설정
```

---

## 🚀 설치 및 실행

이 프로젝트는 백엔드와 프론트엔드 서버를 각각 실행해야 합니다.

### 1. Backend 실행 (Spring Boot)

```bash
# spring-board-project 폴더로 이동
cd spring-board-project

# Gradle 빌드 및 실행 (Windows)
./gradlew bootRun

# Mac/Linux
./gradlew bootRun
```

- **서버 포트**: 8080
- **H2 콘솔**: http://localhost:8080/h2-console
- **이미지 저장 경로**: 프로젝트 루트의 `/uploads` 폴더 자동 생성

### 2. Frontend 실행 (React)

새로운 터미널을 열고 실행합니다.

```bash
# 루트 경로 (package.json이 있는 곳)
npm install
npm run dev
```

- **클라이언트 주소**: http://localhost:5173
- `vite.config.js`에 Proxy 설정이 되어 있어 `/api` 요청이 백엔드(8080)로 전달됩니다.

---

## 📡 API 명세 및 상태 관리

Zustand를 사용하여 백엔드 API와 통신하고 데이터를 관리합니다.

### AuthStore (`/api/members`)

| 기능 | Method | Endpoint | 설명 |
|------|--------|----------|------|
| 로그인 | POST | `/login` | 성공 시 유저 정보를 LocalStorage 및 State에 저장 |
| 회원가입 | POST | `/signup` | 신규 회원 등록 |
| 정보수정 | PUT | `/{id}` | 주소, 비밀번호 변경 |
| 회원탈퇴 | DELETE | `/{id}` | Body에 비밀번호 포함하여 본인 확인 후 삭제 |

### ItemStore (`/api/products`)

| 기능 | Method | Endpoint | 설명 |
|------|--------|----------|------|
| 목록 조회 | GET | `/` | 키워드, 카테고리 필터링 지원 |
| 상세 조회 | GET | `/{id}` | 상품 정보 및 댓글 포함 조회 |
| 상품 등록 | POST | `/` | FormData를 사용해 이미지 파일과 JSON 데이터 전송 |
| 상품 수정 | PUT | `/{id}` | 이미지 교체 및 정보 수정 |
| 상태 변경 | PUT | `/{id}` | FOR_SALE ↔ SOLD_OUT 등 상태 변경 |
| 찜하기 | POST | `/{id}/wishlist` | 찜 등록/해제 토글 |

---

## 📄 페이지 구성

| 페이지 | 경로 | 주요 기능 | 접근 권한 |
|--------|------|-----------|-----------|
| 홈 | `/` | 최신 매물 확인, 카테고리 바로가기 | 전체 |
| 로그인/가입 | `/login`, `/signup` | 사용자 인증 | 비회원 |
| 물품 목록 | `/items` | 검색, 카테고리 필터, 리스트 조회 | 전체 |
| 물품 상세 | `/items/:id` | 상세 정보, 댓글 작성, 찜하기, 상태 변경(판매자) | 전체 / 회원 |
| 물품 등록 | `/items/new` | 사진 및 판매글 등록 | 회원 |
| 물품 수정 | `/items/edit/:id` | 내용 수정, 사진 변경 | 작성자 |
| 마이페이지 | `/mypage` | 내 정보 수정, 판매 내역, 찜 목록, 회원 탈퇴 | 회원 |

---

## 🐛 트러블 슈팅

### 1. JPA Entity와 Setter 지양

**문제**: 무분별한 `@Setter` 사용으로 객체의 일관성을 해칠 우려가 있었음.

**해결**: Entity에서 `@Setter`를 제거하고, `updateProduct`, `updateMember`와 같은 명확한 비즈니스 메서드를 구현하여 DTO를 통해 안전하게 수정하도록 리팩토링함.

### 2. 파일 업로드와 JSON 데이터 전송

**문제**: MultipartFile(이미지)과 DTO(JSON)를 한 번에 보내는 과정에서 Content-Type 문제 발생.

**해결**: 프론트엔드에서 FormData 객체를 생성하여 파일과 텍스트 데이터를 append 한 후 전송. 백엔드 ResourceHandler를 통해 로컬 폴더(`/uploads`)의 이미지를 URL로 서빙하도록 설정.

### 3. 회원 탈퇴 시 참조 무결성 오류

**문제**: 회원을 삭제하려 할 때, 작성한 댓글이나 찜 목록(Foreign Key)이 남아있어 DB 에러 발생.

**해결**: `MemberServiceImpl`의 `deleteMember` 메서드에서 찜 목록 삭제 → 댓글 삭제 → 상품 삭제 → 회원 삭제 순서로 연관 데이터를 먼저 정리하는 로직 구현.

---

## 👨‍💻 개발자

| 이름 | 역할 | 깃허브 |
|------|------|--------|
| 신한서 | Full Stack (React + Spring Boot) | [@hanseo-lab](https://github.com/hanseo-lab) |

---

**Made with React ⚛️ & Spring Boot 🍃**