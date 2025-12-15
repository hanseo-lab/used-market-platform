# 🛒 중고거래 마켓 플랫폼 (Used Market Platform)

**Spring Boot(Backend)**와 **React(Frontend)**를 사용하여 구축한 풀스택 중고 물품 거래 플랫폼입니다.  
사용자는 물품을 등록하고, 검색 및 필터링할 수 있으며, 댓글과 찜하기 기능을 통해 판매자와 소통할 수 있습니다.

---

## 📋 목차

- [주요 기능](#-주요-기능)
- [기술 스택](#-기술-스택)
- [프로젝트 구조](#-프로젝트-구조)
- [DB 설계 및 상태 관리](#-db-설계-및-상태-관리)
- [설치 및 실행](#-설치-및-실행)
- [향후 개선 사항](#-향후-개선-사항-to-do)
- [개발자](#-개발자)

---

## 🎯 주요 기능

### 1. 회원 관리
- ✅ **회원가입/로그인:** JWT 및 세션 기반 인증 (현재는 간편 로그인 구현)
- ✅ **내 정보 수정:** 주소, 비밀번호 변경 가능
- ✅ **마이페이지:** 내 판매 내역 및 관심 상품(찜) 목록 관리

### 2. 물품 거래 (CRUD)
- ✅ **물품 등록:** 제목, 가격, 설명, 카테고리, 이미지 파일 업로드
- ✅ **상태 관리:** 판매자가 직접 `판매중` ↔ `예약중` ↔ `판매완료` 상태 변경
- ✅ **조회 및 검색:**
  - 최신순 정렬 및 조회수 집계
  - 키워드 검색 (제목+내용)
  - 카테고리 필터링 (디지털, 의류, 가구 등)

### 3. 소셜 기능
- ✅ **관심 상품 (찜하기):** 마음에 드는 물품 하트(❤️) 클릭 시 저장
- ✅ **댓글/문의:** 상품 상세 페이지에서 문의 댓글 작성 (판매 완료 시 작성 제한)

---

## 🛠 기술 스택

### Frontend
- **Framework:** React 18, Vite
- **State Management:** Zustand (전역 상태 관리)
- **Styling:** Styled-components (CSS-in-JS)
- **HTTP Client:** Axios

### Backend
- **Framework:** Spring Boot 3.x
- **Language:** Java 17
- **Database:** H2 Database (개발용 인메모리 DB)
- **ORM:** Spring Data JPA (Hibernate)
- **Features:** File Upload, REST API

---

## 📁 프로젝트 구조

```
[Project Root]
├── spring-board-project/   # Backend (Spring Boot)
│   ├── src/main/java/com/kh/board/
│   │   ├── controller/     # API 컨트롤러
│   │   ├── service/        # 비즈니스 로직
│   │   ├── entity/         # DB 테이블 매핑
│   │   ├── repository/     # JPA 레포지토리
│   │   └── dto/            # 데이터 전송 객체
│   └── uploads/            # 업로드된 이미지 저장소
│
└── src/                    # Frontend (React)
    ├── pages/              # 페이지 컴포넌트 (Home, ItemList, Detail...)
    ├── components/         # 재사용 컴포넌트 (Layout, ProtectedRoute...)
    ├── store/              # Zustand 스토어 (auth, item)
    ├── styles/             # 스타일 파일 (*.styled.js)
    └── services/           # Axios 설정
```

---

## 📊 DB 설계 및 상태 관리

### Backend Entity (DB)
- **Member:** 사용자 정보 (이메일, 이름, 비밀번호, 주소)
- **Product:** 상품 정보 (제목, 내용, 가격, 이미지, 카테고리, 상태, 조회수)
- **Reply:** 댓글 (상품-사용자 연관)
- **Wishlist:** 찜 (상품-사용자 연관)

### Frontend Store (Zustand)
- **authStore:** 로그인 유저 정보 관리
- **itemStore:** 상품 목록 조회 및 필터링 관리

---

## 🚀 설치 및 실행

이 프로젝트는 백엔드와 프론트엔드 서버를 각각 실행해야 합니다.

### 1. Backend 실행 (Spring Boot)

```bash
cd spring-board-project

# Windows
gradlew bootRun

# Mac/Linux
./gradlew bootRun
```

- **서버 주소:** http://localhost:8080
- **H2 콘솔:** http://localhost:8080/h2-console

### 2. Frontend 실행 (React)

```bash
# 프로젝트 루트(src 폴더가 있는 곳)에서 실행
npm install
npm run dev
```

- **브라우저 접속:** http://localhost:5173

---

## 🚧 향후 개선 사항 (To-Do)

- [ ] **페이지네이션 (Pagination):** 상품 목록이 많아질 경우 페이징 처리
- [ ] **실시간 채팅:** 댓글을 넘어선 웹소켓 기반 1:1 채팅
- [ ] **알림 (Notification):** 내 글에 댓글이 달리거나 가격 변동 시 알림
- [ ] **프로필 이미지:** 사용자 프로필 사진 업로드 기능
- [ ] **OAuth2 로그인:** 카카오/구글 소셜 로그인 연동

---

## 👨‍💻 개발자

**신한서** - Full Stack Developer
