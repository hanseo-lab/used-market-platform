# 🛒 Spring Boot + React 중고 거래 플랫폼

Spring Boot 백엔드와 React 프론트엔드를 연동한 풀스택 중고 물품 거래 플랫폼입니다.

RESTful 설계 원칙을 준수하고, Spring Security와 JWT를 도입하여 안전하고 확장성 있는 Stateless 인증 시스템을 구현했습니다.

---

## 📋 목차

- [주요 기능](#주요-기능)
- [기술 스택](#기술-스택)
- [프로젝트 구조](#프로젝트-구조)
- [보안 및 인증](#보안-및-인증-jwt)
- [설치 및 실행](#설치-및-실행)
- [API 명세](#api-명세)
- [트러블 슈팅 및 설계 의도](#트러블-슈팅-및-설계-의도)

---

## 🎯 주요 기능

### 1. 보안 및 회원 관리 (Security & Member)

- ✅ **JWT 기반 인증**: Access Token을 활용한 Stateless 로그인 구현
- ✅ **Spring Security 적용**: 필터 체인을 통한 요청 URL별 권한 제어 (인증/인가)
- ✅ **회원가입**: 비밀번호 암호화(BCrypt) 저장
- ✅ **회원 정보 수정/탈퇴**: 본인 인증(JWT) 후 정보 수정 및 탈퇴 처리

### 2. 물품 거래 (Product)

- ✅ **물품 등록**: 이미지 파일(Multipart) 및 상품 데이터(JSON) 복합 전송
- ✅ **물품 조회**: 최신순 목록, 카테고리 필터링, 키워드 검색, 상세 조회 (비회원 가능)
- ✅ **물품 수정/삭제**: **작성자 본인(Token 검증)**만 가능
- ✅ **상태 관리**: 판매중 / 예약중 / 판매완료 상태 변경
- ✅ **댓글 & 찜하기**: 회원 전용 기능 (인증된 사용자만 접근 가능)

---

## 🛠 기술 스택

### Backend

- **Framework**: Spring Boot 3.x, Spring Security
- **Language**: Java 17
- **Database**: H2 (Dev), MySQL (Prod)
- **Persistence**: Spring Data JPA (Hibernate)
- **Security**: JWT (jjwt), BCryptPasswordEncoder
- **Build Tool**: Gradle

### Frontend

- **Framework**: React 18, Vite
- **State Management**: Zustand (Auth Store 구현)
- **Networking**: Axios (Interceptors로 Token 자동 포함)
- **Styling**: Styled-components
- **Routing**: React Router DOM v6

---

## 📁 프로젝트 구조

기존 구조에서 Security 관련 패키지와 Auth 로직이 추가되었습니다.

```
root/
├── spring-board-project/             # Backend
│   ├── src/main/java/com/kh/board/
│   │   ├── controller/               # Member, Product, Auth Controller
│   │   ├── service/                  # 비즈니스 로직
│   │   ├── repository/               # JPA Repository
│   │   ├── entity/                   # DB Entity
│   │   ├── dto/                      # Request/Response DTO
│   │   ├── global/
│   │   │   ├── config/               # SecurityConfig, WebConfig
│   │   │   ├── security/             # JwtTokenProvider, Filter, EntryPoint
│   │   │   ├── exception/            # GlobalExceptionHandler
│   │   │   └── common/               # 공통 Enum (Role 등)
│   │   └── resources/
│   │       └── application.yaml      # 설정 (JWT Secret 포함)
│
└── src/                              # Frontend
    ├── api/                          # Axios Instance (Interceptor 설정)
    ├── store/                        # Zustand (authStore, itemStore)
    ├── components/                   # Layout, ProtectedRoute
    ├── pages/                        # 페이지 컴포넌트
    ├── styles/                       # Styled-components
    └── routes/                       # 라우터 설정
```

---

## 🔐 보안 및 인증 (JWT)

기존의 단순 DB 조회 로그인 방식에서 표준 보안 프레임워크로 고도화했습니다.

### 인증 흐름

1. **로그인 시도**: `POST /api/auth/login` 요청
2. **토큰 발급**: 이메일/비밀번호 검증 성공 시, 서버는 **Access Token (JWT)**을 발급하여 응답
3. **토큰 저장**: 프론트엔드(authStore)에서 로컬 스토리지에 토큰 저장
4. **API 요청**: 인증이 필요한 API 호출 시, Axios Interceptor가 헤더에 자동으로 토큰을 실어 보냄
   - `Authorization: Bearer {Access_Token}`
5. **검증**: JwtAuthenticationFilter가 요청을 가로채 토큰의 유효성을 검증하고 SecurityContext에 인증 정보를 설정

---

## 🚀 설치 및 실행

### 1. Backend 실행

```bash
cd spring-board-project
./gradlew bootRun
```

초기 계정: 서버 시작 시 H2 DB에 테스트 데이터가 로드될 수 있습니다.

### 2. Frontend 실행

```bash
# 루트 경로
npm install
npm run dev
```

---

## 📡 API 명세

> 인증이 필요한 요청에는 반드시 Header에 `Authorization: Bearer <token>`이 포함되어야 합니다.

### 1. 인증 (Auth) - 신규 추가

#### POST /api/auth/login
**설명**: 사용자 인증 및 JWT 발급

**Request**:
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response (200 OK)**:
```json
{
  "accessToken": "eyJhGcio...",
  "tokenType": "Bearer",
  "expiresIn": 3600
}
```

### 2. 회원 (Member)

- `POST /api/members/signup`: 회원가입 (비밀번호 암호화 저장)
- `GET /api/members/me`: 내 정보 조회 (Header 토큰 필수)
- `PUT /api/members/me`: 내 정보 수정 (Header 토큰 필수)
- `DELETE /api/members/me`: 회원 탈퇴 (Header 토큰 필수, 비밀번호 검증 포함)

### 3. 상품 (Product)

- `GET /api/products`: 전체 조회 (페이징/검색 지원, 인증 불필요)
- `POST /api/products`: 상품 등록 (인증 필수, Multipart/form-data)
- `PUT /api/products/{id}`: 상품 수정 (작성자 본인만 가능)
- `DELETE /api/products/{id}`: 상품 삭제 (작성자 본인만 가능)
- `POST /api/products/{id}/wishlist`: 찜하기 토글 (인증 필수)

---

## 🐛 트러블 슈팅 및 설계 의도

### 1. JWT & Spring Security 도입 이유

**기존 문제**: 세션이나 토큰 없이 단순히 DB에서 ID/PW를 조회하는 방식은 보안에 취약하고, 클라이언트의 상태(로그인 여부)를 서버가 계속 기억해야 하는(Stateful) 부담이 있었습니다.

**해결 방안**:
- Spring Security + JWT를 도입하여 Stateless한 아키텍처를 구축했습니다.
- 서버는 세션을 저장하지 않아 확장성이 유리해졌습니다.
- JwtAuthenticationFilter를 통해 컨트롤러 도달 전, 전역적으로 인가(Authorization) 처리를 수행하여 비즈니스 로직과 보안 로직을 분리했습니다.

### 2. Axios Interceptor를 통한 토큰 관리

**문제**: 로그인이 필요한 모든 API 요청마다 `headers: { Authorization: ... }`를 수동으로 넣는 것은 코드 중복이 심하고 실수를 유발했습니다.

**해결 방안**: `src/api/axios.js`에 Request Interceptor를 설정하여, 로컬 스토리지에 토큰이 있다면 자동으로 헤더에 주입되도록 구현했습니다.

```javascript
// src/api/axios.js
instance.interceptors.request.use((config) => {
  const token = localStorage.getItem('accessToken');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});
```

### 3. Entity 보호와 DTO 변환

- **Entity**: DB 테이블과 매핑되는 핵심 객체 (Member, Product)
- **DTO**: API 통신을 위한 객체 (LoginRequestDto, ProductResponseDto)

**설계**: Controller 단에서 절대 Entity를 반환하지 않고, 반드시 DTO로 변환하여 응답합니다. 이를 통해 순환 참조 문제를 방지하고, 비밀번호 등 민감 정보를 클라이언트에 노출하지 않습니다.

### 4. CORS & Proxy 설정

개발 환경(Localhost)에서 프론트엔드(5173)와 백엔드(8080)의 포트가 달라 발생하는 CORS 문제를 해결하기 위해:

- **Frontend**: Vite Proxy 설정을 통해 `/api` 요청을 백엔드로 우회
- **Backend**: WebConfig 및 SecurityConfig에 CORS 허용 설정 추가 (allowedOrigins, exposedHeaders 등)

---

## 👨‍💻 개발자

| 이름 | 역할 | 깃허브 |
|------|------|--------|
| 신한서 | Full Stack (React + Spring Boot) | @hanseo-lab |

---

Made with React ⚛️ & Spring Boot 🍃