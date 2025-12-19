import { create } from 'zustand';
import axios from 'axios';

const useItemStore = create((set) => ({
  items: [],
  totalPages: 0,

  // sort와 size 매개변수 추가 (기본값 설정)
  fetchItems: async (keyword = '', category = '', sort = 'createdAt,desc', size = 12) => {
    try {
      let url = '/api/products';
      const params = new URLSearchParams();
      
      if (keyword) params.append('keyword', keyword);
      if (category && category !== '전체') params.append('category', category);
      
      // 정렬 및 사이즈 파라미터 추가
      params.append('sort', sort);
      params.append('size', size);
      // 기본적으로 1페이지(0번)를 요청
      params.append('page', 0);

      const response = await axios.get(`${url}?${params.toString()}`);
      
      if (response.data.content) {
        set({ 
            items: response.data.content,
            totalPages: response.data.totalPages
        });
      } else {
        set({ items: response.data });
      }
      
    } catch (error) {
      console.error('상품 목록 로딩 실패', error);
      set({ items: [] });
    }
  },

  getItemById: async (id) => {
    try {
      const response = await axios.get(`/api/products/${id}`);
      return response.data;
    } catch (error) {
      console.error('상품 상세 로딩 실패', error);
      return null;
    }
  },

  deleteItem: async (id) => {
    try {
      await axios.delete(`/api/products/${id}`);
      set((state) => ({ items: state.items.filter((item) => item.id !== id) }));
    } catch (error) {
      console.error('삭제 실패', error);
    }
  }
}));

export default useItemStore;