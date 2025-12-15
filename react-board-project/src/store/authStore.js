import { create } from 'zustand';
import axios from 'axios';

const API_URL = '/api/members'

const useAuthStore = create((set) => ({
  user: JSON.parse(localStorage.getItem('user')) || null, // 새로고침 시 로그인 유지용
  
  login: async (email, password) => {
    try {
      const response = await axios.post(`${API_URL}/login`, { email, password });
      const user = response.data;
      
      localStorage.setItem('user', JSON.stringify(user)); // 로그인 유지
      set({ user });
      return user;
    } catch (error) {
      console.error(error);
      throw new Error('로그인 실패: 아이디나 비밀번호를 확인하세요.');
    }
  },
  
  signup: async (userData) => { // email, password, name
    try {
      const response = await axios.post(`${API_URL}/signup`, userData);
      return response.data;
    } catch (error) {
      throw new Error('회원가입 실패: 이미 존재하는 이메일일 수 있습니다.');
    }
  },

  logout: () => {
    localStorage.removeItem('user');
    set({ user: null });
  },
  
  isAuthenticated: () => {
    return !!localStorage.getItem('user');
  }
}));

export default useAuthStore;