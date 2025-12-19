import { create } from 'zustand';
import axios from 'axios';

const useItemStore = create((set) => ({
  items: [],
  totalPages: 0, // (선택사항) 나중에 페이지네이션 버튼 만들 때 사용 가능
  
  // 전체 조회 / 검색 / 카테고리
  fetchItems: async (keyword = '', category = '') => {
    try {
      let url = '/api/products';
      const params = new URLSearchParams();
      if (keyword) params.append('keyword', keyword);
      if (category && category !== '전체') params.append('category', category);
      
      // 백엔드 기본 설정(@PageableDefault)에 따라 page=0, size=10으로 요청됨
      // 더 많은 데이터를 한 번에 보고 싶다면 아래 주석을 해제하세요
      // params.append('size', 100); 

      const response = await axios.get(`${url}?${params.toString()}`);
      
      // [핵심 수정] 
      // 백엔드가 Page 객체({ content: [...], totalPages: ... })를 반환하므로
      // 실제 상품 목록인 'content'만 꺼내서 items에 넣어야 합니다.
      if (response.data.content) {
        set({ 
            items: response.data.content,
            totalPages: response.data.totalPages
        });
      } else {
        // 혹시라도 백엔드가 배열을 줄 경우를 대비한 방어 코드
        set({ items: response.data });
      }
      
    } catch (error) {
      console.error('상품 목록 로딩 실패', error);
      // 에러 발생 시 빈 배열로 초기화하여 map/slice 에러 방지
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