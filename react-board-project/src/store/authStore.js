import { create } from 'zustand';
import { persist } from 'zustand/middleware';
import api from '../api/axios'; // [중요] axios 인스턴스 import (경로 확인 필요)

const useAuthStore = create(
  persist(
    (set, get) => ({
      // State
      user: null,
      token: null,
      isAuthenticated: false,

      // [1] 회원가입 Action (백엔드 주소 수정됨)
      signup: async (userData) => {
        try {
          // 백엔드 MemberController의 @PostMapping("/signup") 주소와 일치시킴
          const response = await api.post('/api/members/signup', userData);
          return response.data;
        } catch (error) {
          console.error("회원가입 에러:", error);
          // 에러 메시지 추출
          const message = error.response?.data?.message || "회원가입 중 오류가 발생했습니다.";
          throw new Error(message);
        }
      },

      // [2] 로그인 Action (API 호출 추가)
      login: async (email, password) => {
        try {
          // 백엔드 AuthController의 @PostMapping("/login") 주소 호출
          const response = await api.post('/api/auth/login', { email, password });

          // 백엔드 응답 구조(AuthDto.LoginResponse)에 맞춰 데이터 추출
          // response.data = { accessToken, grantType, email, name, ... }
          const { accessToken, email: userEmail, name } = response.data;

          // 토큰 저장 (axios 인터셉터에서 사용할 수 있도록 localStorage에도 저장)
          localStorage.setItem('accessToken', accessToken);

          // 상태 업데이트
          set({
            token: accessToken,
            user: { email: userEmail, name: name },
            isAuthenticated: true,
          });

          return true;
        } catch (error) {
          console.error("로그인 에러:", error);
          const message = error.response?.data?.message || "로그인 실패: 이메일이나 비밀번호를 확인하세요.";
          throw new Error(message);
        }
      },

      // [3] 로그아웃 Action
      logout: () => {
        localStorage.removeItem('accessToken'); // 토큰 삭제
        localStorage.removeItem('auth-storage'); // Persist 스토리지 삭제
        set({
          user: null,
          token: null,
          isAuthenticated: false,
        });
      },

      // 유틸리티 함수들
      checkAuth: () => {
        const { token } = get();
        return !!token;
      },
    }),
    {
      name: 'auth-storage', // 새로고침 해도 로그인 유지 (LocalStorage에 저장)
      partialize: (state) => ({
        token: state.token,
        user: state.user,
        isAuthenticated: state.isAuthenticated,
      }),
    }
  )
);

export default useAuthStore;