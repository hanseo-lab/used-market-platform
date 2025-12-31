import axios from 'axios';

// Axios 인스턴스 생성
const api = axios.create({
  baseURL: '', // 필요하다면 백엔드 주소 (예: http://localhost:8080)
});

// 요청 인터셉터 : 모든 요청 전에 토큰을 헤더에 넣음
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('accessToken');
    if (token) {
      // 백엔드 JwtFilter에서 "Bearer " 접두사를 체크하므로 꼭 붙여야 합니다.
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default api;