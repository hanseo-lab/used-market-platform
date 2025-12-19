import { Link } from 'react-router-dom';
import styled from 'styled-components';

export const Container = styled.div`
  max-width: 1000px;
  margin: 0 auto;
`;

export const Title = styled.h1`
  margin-bottom: 2rem;
  color: #333;
`;

export const Section = styled.section`
  background: white;
  border-radius: 10px;
  padding: 2rem;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  margin-bottom: 2rem;
`;

// [추가] 인라인 스타일(display: flex...)을 대체할 컴포넌트
export const SectionHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
`;

export const SectionTitle = styled.h2`
  margin-bottom: 0; /* SectionHeader 안에서 정렬을 위해 마진 제거 (필요시 조정) */
  color: #333;
`;

export const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

export const FormGroup = styled.div`
  display: flex;
  flex-direction: column;
`;

export const Label = styled.label`
  margin-bottom: 0.5rem;
  color: #333;
  font-weight: 500;
`;

export const Input = styled.input`
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 1rem;
  
  &:focus {
    outline: none;
    border-color: #007bff;
  }
  
  &:disabled {
    background-color: #f5f5f5;
    cursor: not-allowed;
  }
`;

export const ButtonGroup = styled.div`
  display: flex;
  gap: 1rem;
  justify-content: flex-end;
`;

export const Button = styled.button`
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-weight: 600;
  background-color: ${props => props.$variant === 'secondary' ? '#6c757d' : '#007bff'};
  color: white;
  
  &:hover {
    opacity: 0.9;
  }
`;

export const Error = styled.div`
  color: #dc3545;
  font-size: 0.9rem;
  margin-top: 0.5rem;
`;

export const Success = styled.div`
  color: #28a745;
  font-size: 0.9rem;
  margin-top: 0.5rem;
`;

export const ItemList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

export const ItemCard = styled.div`
  display: flex;
  gap: 1rem;
  padding: 1rem;
  border: 1px solid #eee;
  border-radius: 5px;
  transition: background-color 0.2s;
  
  &:hover {
    background-color: #f8f9fa;
  }
`;

export const ItemImage = styled.div`
  width: 100px;
  height: 100px;
  background-color: #f0f0f0;
  background-image: url(${props => props.src});
  background-size: cover;
  background-position: center;
  border-radius: 5px;
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #999;
  font-size: 0.8rem;
`;

export const ItemInfo = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

export const ItemTitle = styled.h3`
  font-size: 1.1rem;
  margin-bottom: 0.5rem;
  color: #333;
`;

export const ItemPrice = styled.p`
  font-size: 1.2rem;
  font-weight: bold;
  color: #007bff;
`;

export const ItemActions = styled.div`
  display: flex;
  gap: 0.5rem;
  align-items: center;
`;

export const StyledLink = styled(Link)`
  padding: 0.5rem 1rem;
  background-color: #007bff;
  color: white;
  text-decoration: none;
  border-radius: 5px;
  font-size: 0.9rem;
  
  &:hover {
    opacity: 0.9;
  }
`;

export const DeleteButton = styled.button`
  padding: 0.5rem 1rem;
  background-color: #dc3545;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 0.9rem;
  
  &:hover {
    opacity: 0.9;
  }
`;

export const EmptyState = styled.div`
  text-align: center;
  padding: 2rem;
  color: #666;
`;

export const WithdrawButton = styled.button`
  margin-top: 3rem;
  padding: 0.5rem 1rem;
  background-color: transparent;
  color: #999;
  border: 1px solid #ddd;
  border-radius: 5px;
  cursor: pointer;
  font-size: 0.85rem;
  align-self: flex-end; 

  &:hover {
    color: #dc3545;
    border-color: #dc3545;
    background-color: #fff5f5;
  }
`;

// 모달 배경
export const ModalOverlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
`;

// 모달 박스
export const ModalContent = styled.div`
  background: white;
  padding: 2rem;
  border-radius: 10px;
  width: 400px;
  max-width: 90%;
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

// 모달 제목
export const ModalTitle = styled.h3`
  margin: 0 0 1rem 0;
  color: #333;
`;

// 모달 버튼 그룹
export const ModalActions = styled.div`
  display: flex;
  justify-content: flex-end;
  gap: 0.5rem;
  margin-top: 1rem;
`;