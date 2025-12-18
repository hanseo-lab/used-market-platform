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
      throw new Error('로그인 실패: 아이디나 비밀번호를 확인하세요.');
    }
  },
  
  signup: async (userData) => { 
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
  
  // [수정] password를 인자로 받아서 서버에 전송
  deleteAccount: async (password) => {
    const user = get().user;
    if (!user) return;

    try {
      // DELETE 요청의 body에 데이터를 보내려면 data 속성을 사용해야 함
      await axios.delete(`${API_URL}/${user.id}`, {
        data: { password: password } 
      });
      
      localStorage.removeItem('user');
      set({ user: null });
    } catch (error) {
      // 서버에서 보낸 에러 메시지 활용 (비밀번호 불일치 등)
      const message = error.response?.data?.message || error.response?.data || '회원 탈퇴 실패';
      throw new Error(message); 
    }
  },

  isAuthenticated: () => {
    return !!localStorage.getItem('user');
  }
}));

export default useAuthStore;