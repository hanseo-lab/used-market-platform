import { create } from 'zustand';
import axios from 'axios';

const API_URL = '/api/members';

const useAuthStore = create((set, get) => ({
  user: JSON.parse(localStorage.getItem('user')) || null, 

  login: async (email, password) => {
    try {
      const response = await axios.post(`${API_URL}/login`, { email, password });
      const user = response.data;
      localStorage.setItem('user', JSON.stringify(user));
      set({ user });
      return user;
    } catch (error) {
      console.error(error);
      const message = error.response?.data?.message || '로그인 실패: 아이디나 비밀번호를 확인하세요.';
      throw new Error(message);
    }
  },
  
  signup: async (userData) => { 
    try {
      const response = await axios.post(`${API_URL}`, userData);
      return response.data;
    } catch (error) {
      const message = error.response?.data?.message || '회원가입 실패: 서버 오류가 발생했습니다.';
      throw new Error(message);
    }
  },

  logout: () => {
    localStorage.removeItem('user');
    set({ user: null });
  },
  
  deleteAccount: async (password) => {
    const user = get().user;
    if (!user) return;

    try {
      await axios.delete(`${API_URL}/${user.id}`, {
        headers: { password: password } 
      });
      
      localStorage.removeItem('user');
      set({ user: null });
    } catch (error) {
      const message = error.response?.data?.message || '회원 탈퇴 실패';
      throw new Error(message); 
    }
  },

  isAuthenticated: () => {
    return !!localStorage.getItem('user');
  }
}));

export default useAuthStore;