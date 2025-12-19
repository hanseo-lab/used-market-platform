# 중고거래 마켓 플랫폼

React를 사용한 중고 물품 거래 플랫폼입니다. 사용자는 물품을 등록하고, 구매 희망 댓글을 남길 수 있습니다.

## 📋 목차

- [주요 기능](#주요-기능)
- [기술 스택](#기술-스택)
- [프로젝트 구조](#프로젝트-구조)
- [설치 및 실행](#설치-및-실행)
- [페이지 구성](#페이지-구성)
- [주요 컴포넌트](#주요-컴포넌트)
- [상태 관리](#상태-관리)

## 🎯 주요 기능

### 1. 회원 관리
- ✅ 회원가입 (이메일, 비밀번호, 이름, 전화번호)
- ✅ 로그인/로그아웃
- ✅ 마이페이지에서 정보 수정
- ✅ 입력 폼 유효성 검사 (Validation)

### 2. 물품 거래
- ✅ 물품 등록 (제목, 가격, 설명, 이미지)
- ✅ 물품 수정/삭제 (작성자만 가능)
- ✅ 물품 목록 조회
- ✅ 물품 상세 정보 확인
- ✅ 검색 기능 (제목, 설명)
- ✅ 정렬 기능 (최신순, 오래된순, 낮은 가격순, 높은 가격순)

### 3. 댓글 시스템
- ✅ 구매 희망 댓글 작성
- ✅ 댓글 수정/삭제 (작성자만 가능)
- ✅ 실시간 댓글 목록 표시

### 4. 라우팅
- ✅ SPA 방식의 페이지 전환
- ✅ 보호된 라우트 (로그인 필요)
- ✅ 404 에러 페이지

## 🛠 기술 스택

- **Frontend Framework**: React 18
- **Routing**: React Router DOM v6
- **State Management**: 
  - Zustand (전역 상태)
  - Context API (컨텍스트 제공)
- **Styling**: Styled-components
- **Data Storage**: LocalStorage
- **Build Tool**: Vite

## 📁 프로젝트 구조

```
src/
├── store/                 # Zustand 스토어
│   ├── authStore.js       # 인증 관련 상태 관리
│   └── itemStore.js       # 물품 관련 상태 관리
├── context/               # Context API
│   ├── AuthContext.jsx    # 인증 컨텍스트
│   └── ItemContext.jsx    # 물품 컨텍스트
├── components/            # 재사용 가능한 컴포넌트
│   ├── Layout.jsx         # 레이아웃 (헤더, 네비게이션)
│   └── ProtectedRoute.jsx # 보호된 라우트
├── pages/                 # 페이지 컴포넌트
│   ├── HomePage.jsx       # 홈페이지
│   ├── LoginPage.jsx      # 로그인
│   ├── SignupPage.jsx     # 회원가입
│   ├── ItemListPage.jsx   # 물품 목록
│   ├── ItemDetailPage.jsx # 물품 상세
│   ├── ItemFormPage.jsx   # 물품 등록/수정
│   ├── MyPage.jsx         # 마이페이지
│   └── NotFoundPage.jsx   # 404 페이지
├── styles/                # 스타일 (Styled Components)
│   ├── Home.styled.js
│   ├── ItemDetail.styled.js
│   ├── ItemForm.styled.js
│   ├── ItemList.styled.js
│   ├── Layout.styled.js
│   ├── Login.styled.js
│   ├── Mypage.styled.js
│   ├── NotFound.styled.js
│   └── Signup.styled.js
├── routes/                # 라우팅 설정
│   └── index.jsx          # 라우트 정의
├── App.jsx                # 메인 앱 컴포넌트
├── main.jsx               # 진입점
└── index.css              # 글로벌 스타일
```

## 🚀 설치 및 실행

### 1. 저장소 클론 (또는 파일 다운로드)

```bash
git clone <repository-url>
cd <project-folder>
```

### 2. 패키지 설치

```bash
npm install
```

필요한 패키지:
```bash
npm install react-router-dom zustand styled-components
```

### 3. 개발 서버 실행

```bash
npm run dev
```

브라우저에서 `http://localhost:5173` 접속

### 4. 프로덕션 빌드

```bash
npm run build
```

## 📄 페이지 구성

| 페이지 | 경로 | 설명 | 로그인 필요 |
|--------|------|------|-------------|
| 홈 | `/` | 최근 등록된 물품 표시 | ❌ |
| 로그인 | `/login` | 사용자 로그인 | ❌ |
| 회원가입 | `/signup` | 신규 회원 가입 | ❌ |
| 물품 목록 | `/items` | 전체 물품 리스트 | ❌ |
| 물품 상세 | `/items/:id` | 개별 물품 상세 정보 | ❌ |
| 물품 등록 | `/items/new` | 새 물품 등록 | ✅ |
| 물품 수정 | `/items/edit/:id` | 기존 물품 수정 | ✅ (작성자) |
| 마이페이지 | `/mypage` | 내 정보 및 등록한 물품 관리 | ✅ |
| 404 | `/*` | 존재하지 않는 페이지 | ❌ |

## 🧩 주요 컴포넌트

### Layout.jsx
- 헤더와 네비게이션 바를 포함한 전체 레이아웃
- 로그인 상태에 따른 메뉴 표시
- 모든 페이지에 공통으로 적용

### ProtectedRoute.jsx
- 인증이 필요한 페이지 보호
- 비로그인 시 로그인 페이지로 리다이렉트

## 📊 상태 관리

### Zustand Store

**authStore.js**
```javascript
{
  user: null,              // 현재 로그인한 사용자
  login: (userData) => {}, // 로그인
  logout: () => {},        // 로그아웃
  updateUser: (data) => {} // 사용자 정보 수정
}
```

**itemStore.js**
```javascript
{
  items: [],                          // 물품 목록
  addItem: (item) => {},              // 물품 추가
  updateItem: (id, data) => {},       // 물품 수정
  deleteItem: (id) => {},             // 물품 삭제
  addComment: (itemId, comment) => {} // 댓글 추가
  // ... 기타 댓글 관련 메서드
}
```

### Context API

- **AuthContext**: 인증 관련 로직과 메서드 제공
- **ItemContext**: 물품 관련 로직과 메서드 제공

## 💾 데이터 저장

LocalStorage를 사용하여 데이터를 영구 저장합니다:

- `users`: 전체 사용자 정보
- `user`: 현재 로그인한 사용자
- `items`: 전체 물품 정보 (댓글 포함)

## 🔐 권한 관리

### 물품 수정/삭제
- 작성자 본인만 수정 및 삭제 가능
- `authorId`와 현재 로그인한 `user.id` 비교

### 댓글 수정/삭제
- 댓글 작성자 본인만 수정 및 삭제 가능
- `comment.authorId`와 현재 로그인한 `user.id` 비교

## 📝 입력 유효성 검사

### 회원가입
- 이메일 형식 검사
- 비밀번호 최소 6자 이상
- 비밀번호 확인 일치 여부
- 전화번호 형식 (010-1234-5678)
- 중복 이메일 체크

### 물품 등록
- 제목 필수 입력
- 가격 유효성 (숫자, 0 이상)
- 설명 필수 입력

## 🎨 스타일링

Styled-components를 사용한 컴포넌트 단위 스타일링:
- 동적 스타일링 지원
- Props 기반 조건부 스타일
- 재사용 가능한 스타일 컴포넌트

## 🔍 검색 및 정렬

### 검색
- 물품 제목과 설명에서 키워드 검색
- 실시간 필터링

### 정렬 옵션
- 최신순
- 오래된순
- 낮은 가격순
- 높은 가격순

## 🐛 문제 해결

### LocalStorage 데이터 초기화

```javascript
// 브라우저 콘솔에서 실행
localStorage.clear();
```

### 로그인 상태 확인

```javascript
// 브라우저 콘솔에서 실행
console.log(JSON.parse(localStorage.getItem('user')));
```

## 📱 반응형 디자인

- Grid 레이아웃으로 다양한 화면 크기 지원
- `auto-fill`, `minmax`를 활용한 유연한 레이아웃

## 🚧 향후 개선 사항

- [ ] 페이지네이션
- [ ] 채팅 기능
- [ ] 사용자 프로필 이미지
- [ ] 알림 기능

## 📄 라이센스

MIT License

## 👨‍💻 개발자
| 이름 | 역할 | 깃허브 | 이메일 |
| :---: | :---: | :---: | :---: |
| 신한서 | Frontend / Design / Planning | [@hanseo-lab](https://github.com/hanseo-lab) | hanseo.lab@gmail.com |
---

**Made with React ⚛️**