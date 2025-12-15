import { create } from 'zustand';
import axios from 'axios';

const useItemStore = create((set) => ({
  items: [],
  
  // 전체 조회 / 검색 / 카테고리
  fetchItems: async (keyword = '', category = '') => {
    try {
      let url = '/api/products';
      const params = new URLSearchParams();
      if (keyword) params.append('keyword', keyword);
      if (category && category !== '전체') params.append('category', category);
      
      const response = await axios.get(`${url}?${params.toString()}`);
      set({ items: response.data });
    } catch (error) {
      console.error('상품 목록 로딩 실패', error);
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