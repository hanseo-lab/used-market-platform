# 🛒 Spring Boot + React 중고 거래 플랫폼 (REST API 과제)

Spring Boot 백엔드와 React 프론트엔드를 연동한 풀스택 중고 물품 거래 플랫폼입니다.  
이번 과제는 **RESTful 설계 원칙**을 준수하여 백엔드 API를 구축하고, 프론트엔드와 데이터를 주고받는 **기본적인 통신 흐름**을 구현하는 데 집중했습니다.

⚠️ **참고 사항**: 이번 과제 범위에서는 **로그인 상태 유지(Session, JWT 등)는 다루지 않으며**, 백엔드는 요청 데이터의 검증과 비즈니스 로직 처리, 그리고 적절한 HTTP 상태 코드 및 JSON 응답을 반환하는 역할에 충실합니다.

---

## 📋 목차

- [주요 기능](#-주요-기능)
- [기술 스택](#-기술-스택)
- [프로젝트 구조](#-프로젝트-구조)
- [설치 및 실행](#-설치-및-실행)
- [API 명세](#-api-명세)
- [HTTP 상태 코드](#-http-상태-코드)
- [페이지 구성](#-페이지-구성)
- [트러블 슈팅 및 설계 의도](#-트러블-슈팅-및-설계-의도)

---

## 🎯 주요 기능

### 1. 회원 관리 (Member) - RESTful API

- ✅ **회원가입**: `POST /api/members` (상태 코드 201 Created 반환)
- ✅ **로그인**: 이메일/비밀번호 검증 후 회원 정보 반환 (세션/토큰 미발급, 순수 데이터 검증)
- ✅ **정보 수정**: 본인 정보(주소, 비밀번호 등) 수정
- ✅ **회원 탈퇴**: 비밀번호 재확인(검증) 후 계정 및 연관 데이터 삭제

### 2. 물품 거래 (Product)

- ✅ **물품 등록**: 이미지 파일 업로드 및 상품 정보 등록 (Multipart FormData)
- ✅ **물품 조회**: 최신순 목록 조회, 카테고리 필터링, 키워드 검색, 상세 조회
- ✅ **물품 수정/삭제**: 작성자 본인만 가능 (이미지 교체 지원)
- ✅ **상태 관리**: 판매중(FOR_SALE) / 예약중(RESERVED) / 판매완료(SOLD_OUT) 상태 변경
- ✅ **댓글 시스템**: 상품에 대한 문의 댓글 작성/조회
- ✅ **찜하기(Wishlist)**: 관심 상품 등록/해제

---

## 🛠 기술 스택

### Backend

- **Framework**: Spring Boot 3.x
- **Language**: Java 17
- **Database**: H2 (Dev), MySQL (Prod)
- **Persistence**: Spring Data JPA (Hibernate)
- **API Design**: RESTful API (DTO Pattern 적용)
- **Build Tool**: Gradle

### Frontend

- **Framework**: React 18, Vite
- **State Management**: Zustand
- **Networking**: Axios (Proxy 설정으로 CORS 해결)
- **Styling**: Styled-components
- **Routing**: React Router DOM v6

---

## 📁 프로젝트 구조

```
root/
├── spring-board-project/      # Backend (Spring Boot)
│   ├── src/main/java/com/kh/board/
│   │   ├── controller/        # REST Controller
│   │   ├── service/           # 비즈니스 로직
│   │   ├── repository/        # JPA Repository
│   │   ├── entity/            # DB Entity (JPA)
│   │   ├── dto/               # Request/Response DTO
│   │   └── config/            # WebConfig (CORS, ResourceHandler)
│   └── resources/
│       └── application.yaml   # DB 및 서버 설정
│
└── src/                       # Frontend (React)
    ├── store/                 # Zustand Stores (Axios 호출)
    ├── components/            # 공통 컴포넌트
    ├── pages/                 # 페이지 컴포넌트
    ├── styles/                # Styled-components 파일
    └── routes/                # 라우터 설정
```

---

## 🚀 설치 및 실행

### 1. Backend 실행 (Spring Boot)

```bash
# spring-board-project 폴더로 이동
cd spring-board-project

# Gradle 빌드 및 실행
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

### 💡 개발 환경 CORS 해결 (Proxy 설정)

개발 단계에서는 `vite.config.js`에 Proxy를 설정하여 브라우저의 CORS 정책을 우회합니다.

```javascript
// vite.config.js
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080', // 백엔드 주소
        changeOrigin: true,
      },
    },
  },
})
```

---

## 📡 API 명세

모든 요청과 응답은 **JSON 형식**을 따르며, Entity를 직접 노출하지 않고 **DTO를 사용**합니다.

### 1. 회원 (Member)

#### POST /api/members (회원가입)

신규 회원을 등록합니다. 성공 시 **201 Created**를 반환합니다.

**Request Body (MemberSignupDto)**
```json
{
  "email": "user@example.com",
  "password": "password123!",
  "name": "홍길동",
  "phone": "010-1234-5678",
  "address": "서울시 강남구"
}
```

**Response (201 Created)**
```json
{
  "id": 1,
  "email": "user@example.com",
  "name": "홍길동",
  "address": "서울시 강남구"
}
```

---

#### POST /api/members/login (로그인)

이메일과 비밀번호를 검증합니다. (이번 과제에서는 토큰 발급 없이 회원 정보만 반환)

**Request Body (LoginRequestDto)**
```json
{
  "email": "user@example.com",
  "password": "password123!"
}
```

**Response (200 OK)**
```json
{
  "id": 1,
  "email": "user@example.com",
  "name": "홍길동",
  "address": "서울시 강남구",
  "role": "USER"
}
```

---

#### PUT /api/members/{id} (회원 정보 수정)

회원의 주소와 비밀번호를 수정합니다.

**Request Body (MemberUpdateDto)**
```json
{
  "address": "서울시 서초구",
  "password": "newPassword123!"
}
```

**Response (200 OK)**
```json
{
  "id": 1,
  "email": "user@example.com",
  "name": "홍길동",
  "address": "서울시 서초구",
  "updatedAt": "2024-03-21T15:30:00"
}
```

---

#### DELETE /api/members/{id} (회원 탈퇴)

비밀번호 검증 후 회원을 삭제합니다. REST 규약상 Body를 가지는 것이 권장되진 않으나, 검증을 위해 데이터를 전송합니다.

**Request Body (MemberWithdrawDto)**
```json
{
  "password": "password123!"
}
```

**Response (200 OK)**
```
"회원 탈퇴가 완료되었습니다."
```

---

### 2. 상품 (Product)

#### GET /api/products (상품 목록 조회)

전체 또는 필터링된 상품 목록을 조회합니다.

**Query Parameters**
- `keyword` (선택): 상품 제목/설명 검색어
- `category` (선택): 카테고리 필터링

**Response (200 OK)**
```json
[
  {
    "id": 10,
    "title": "아이패드 팝니다",
    "price": 500000,
    "status": "FOR_SALE",
    "imageUrl": "/uploads/uuid_file.jpg",
    "viewCount": 5,
    "createdAt": "2024-03-20T10:00:00"
  },
  {
    "id": 11,
    "title": "맥북 프로",
    "price": 1500000,
    "status": "RESERVED",
    "imageUrl": "/uploads/uuid_macbook.jpg",
    "viewCount": 12,
    "createdAt": "2024-03-19T14:22:00"
  }
]
```

**사용 예시**
```
GET /api/products?keyword=아이패드&category=디지털/가전
```

---

#### GET /api/products/{id} (상품 상세 조회)

특정 상품의 상세 정보를 조회하고 조회수를 증가시킵니다.

**Response (200 OK)**
```json
{
  "id": 10,
  "title": "아이패드 팝니다",
  "content": "사용감 거의 없습니다",
  "price": 500000,
  "category": "디지털/가전",
  "status": "FOR_SALE",
  "imageUrl": "/uploads/uuid_file.jpg",
  "originName": "ipad.jpg",
  "viewCount": 6,
  "seller": "홍길동",
  "sellerId": 1,
  "createdAt": "2024-03-20T10:00:00",
  "updatedAt": "2024-03-20T10:00:00",
  "comments": [
    {
      "id": 1,
      "content": "가격 내려갈 수 있나요?",
      "writer": "김철수",
      "createdAt": "2024-03-20T11:30:00"
    }
  ]
}
```

---

#### POST /api/products (상품 등록)

새로운 상품을 등록합니다. 이미지 파일과 상품 정보를 함께 전송합니다. 성공 시 **201 Created**를 반환합니다.

**Content-Type**: `multipart/form-data`

**Request Parts**
- `imageFile` (필수): 상품 이미지 파일 (Binary)
- `title` (필수): 상품명
- `content` (필수): 상품 설명
- `price` (필수): 가격
- `category` (필수): 카테고리
- `seller` (필수): 판매자명

**JavaScript 예시 (Axios)**
```javascript
const formData = new FormData();
formData.append('imageFile', fileInput.files[0]);
formData.append('title', '맥북 프로');
formData.append('content', '상태 좋습니다.');
formData.append('price', 1500000);
formData.append('category', '디지털/가전');
formData.append('seller', '홍길동');

axios.post('/api/products', formData, {
  headers: { 'Content-Type': 'multipart/form-data' }
});
```

**Response (201 Created)**
```json
{
  "id": 11,
  "title": "맥북 프로",
  "originName": "macbook.jpg",
  "imageUrl": "/uploads/uuid_macbook.jpg",
  "status": "FOR_SALE",
  "createdAt": "2024-03-20T16:45:00"
}
```

---

#### PUT /api/products/{id} (상품 수정)

상품의 정보를 수정하거나 판매 상태를 변경합니다.

**Request Body (JSON)**
```json
{
  "title": "가격 인하합니다",
  "price": 1400000,
  "content": "빠른 거래 원합니다",
  "status": "RESERVED"
}
```

**주의**: 상품을 등록한 판매자만 수정 가능합니다.

**Response (200 OK)**
```json
{
  "id": 11,
  "title": "가격 인하합니다",
  "price": 1400000,
  "content": "빠른 거래 원합니다",
  "status": "RESERVED",
  "imageUrl": "/uploads/uuid_macbook.jpg",
  "updatedAt": "2024-03-21T09:15:00"
}
```

---

#### DELETE /api/products/{id} (상품 삭제)

상품을 삭제합니다. 작성자만 삭제 가능합니다.

**Response (200 OK)**
```
"상품이 삭제되었습니다."
```

---

#### POST /api/products/{id}/wishlist (찜하기)

상품을 찜 목록에 추가하거나 해제합니다.

**Response (200 OK)**
```json
{
  "id": 10,
  "isWishlisted": true,
  "wishlistCount": 5
}
```

---

## 🔑 HTTP 상태 코드

| 코드 | 설명 | 발생 상황 |
|------|------|----------|
| `200` | OK (성공) | 조회, 수정, 삭제, 로그인 성공 시 |
| `201` | Created (생성됨) | 회원가입, 상품 등록 성공 시 |
| `400` | Bad Request (잘못된 요청) | 필수 입력값 누락, 비밀번호 불일치, 유효성 검사 실패 |
| `401` | Unauthorized (미인증) | 로그인하지 않은 사용자가 보호된 리소스 접근 시 |
| `403` | Forbidden (접근 금지) | 수정/삭제 권한이 없는 사용자 접근 시 |
| `404` | Not Found (리소스 없음) | 존재하지 않는 게시글/회원 조회 시 |
| `409` | Conflict (충돌) | 중복된 이메일로 가입 시도 시 |
| `500` | Internal Server Error | 이미지 업로드 실패, DB 연결 오류 등 |

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

## 🐛 트러블 슈팅 및 설계 의도

### 1. RESTful URI 설계

**고민**: 기존에는 `/signup`, `/deleteMember` 처럼 행위(동사)가 포함된 URI를 사용했습니다.

**개선**: 리소스(명사) 중심의 URI로 변경하고, 행위는 HTTP Method로 표현했습니다.

```
POST /signup (X) → POST /api/members (O)
DELETE /deleteMember (X) → DELETE /api/members/{id} (O)
GET /getAllProducts (X) → GET /api/products (O)
```

### 2. Entity 직접 노출 방지 (DTO 사용)

**문제**: 컨트롤러에서 JPA Entity(Member)를 직접 반환하면, 비밀번호 같은 민감 정보가 노출되거나 DB 스키마 변경이 API 스펙에 영향을 주는 문제 발생.

**해결**: `MemberSignupDto`(요청), `MemberResponseDto`(응답) 등으로 철저히 분리하여, 필요한 데이터만 주고받도록 설계했습니다.

```java
// ❌ Entity 직접 노출
@PostMapping("/members")
public Member signup(@RequestBody Member member) {
    return memberService.save(member);
}

// ✅ DTO 사용
@PostMapping("/members")
public MemberResponseDto signup(@RequestBody MemberSignupDto dto) {
    Member member = memberService.save(dto);
    return new MemberResponseDto(member);
}
```

### 3. HTTP 상태 코드의 명확한 사용

**적용**: 무조건 200 OK를 반환하는 것이 아니라, 상황에 맞는 코드를 반환하도록 구현했습니다.

```java
@PostMapping("/members")
public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberSignupDto dto) {
    Member member = memberService.save(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(new MemberResponseDto(member));
}

@GetMapping("/products/{id}")
public ResponseEntity<ProductDetailDto> getProduct(@PathVariable Long id) {
    ProductDetailDto product = productService.findById(id);
    return ResponseEntity.ok(product);
}
```

- `201 Created`: 회원가입, 상품 등록 성공 시
- `200 OK`: 조회, 수정, 삭제 성공 시
- `400 Bad Request`: 잘못된 입력값 등 (예외 처리 시)

### 4. 파일 업로드와 JSON 데이터 전송

**문제**: MultipartFile(이미지)과 DTO(JSON)를 한 번에 보내는 과정에서 Content-Type 문제 발생.

**해결**: 프론트엔드에서 FormData 객체를 생성하여 파일과 텍스트 데이터를 append 한 후 전송합니다. 백엔드 ResourceHandler를 통해 로컬 폴더(`/uploads`)의 이미지를 URL로 서빙하도록 설정.

```javascript
// Frontend
const formData = new FormData();
formData.append('imageFile', file);
formData.append('title', title);

axios.post('/api/products', formData);
```

```java
// Backend - ResourceHandler 설정
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/uploads/**")
            .addResourceLocations("file:uploads/");
}
```

### 5. CORS (Cross-Origin Resource Sharing)

**문제**: React(Port 5173)에서 Spring Boot(Port 8080)로 API 요청 시 브라우저 보안 정책에 의해 차단됨.

**해결**: 개발 편의성을 위해 React의 `vite.config.js` Proxy 기능을 사용하여 CORS 문제를 우회했습니다. (배포 시에는 Spring Boot WebConfig 설정 필요)

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
```

---

## 👨‍💻 개발자

| 이름 | 역할 | 깃허브 |
|------|------|--------|
| 신한서 | Full Stack (React + Spring Boot) | [@hanseo-lab](https://github.com/hanseo-lab) |

---

**Made with React ⚛️ & Spring Boot 🍃**