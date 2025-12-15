import styled from "styled-components";
import { Link } from 'react-router-dom';

export const Container = styled.div`
  max-width: 900px;
  margin: 0 auto;
`;

export const BackButton = styled(Link)`
  display: inline-flex;
  align-items: center;
  color: var(--text-sub);
  margin-bottom: 1.5rem;
  font-weight: 500;
  
  &:hover {
    color: var(--primary);
  }
`;

export const ItemContainer = styled.div`
  background: white;
  border-radius: 16px;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05);
  border: 1px solid var(--border);
  overflow: hidden;
`;

export const ItemHeader = styled.div`
  padding: 2rem;
  border-bottom: 1px solid var(--border);
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  
  @media (max-width: 768px) {
    flex-direction: column;
    gap: 1rem;
  }
`;

export const ItemInfoMain = styled.div`
  flex: 1;
`;

export const CategoryBadge = styled.span`
  background-color: #f1f5f9;
  color: var(--text-sub);
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 0.85rem;
  font-weight: 600;
  display: inline-block;
  margin-bottom: 0.8rem;
`;

export const ItemTitle = styled.h1`
  font-size: 1.8rem;
  font-weight: 800;
  margin: 0 0 0.5rem 0;
  color: var(--text-main);
  display: flex;
  align-items: center;
  gap: 0.5rem;
`;

export const StatusTag = styled.span`
  font-size: 0.8em;
  color: ${props => props.$status === 'SOLD_OUT' ? '#ef4444' : '#f59e0b'};
  border: 1px solid currentColor;
  padding: 2px 8px;
  border-radius: 4px;
`;

export const ItemPrice = styled.div`
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--primary);
  margin-top: 0.5rem;
`;

export const ItemMeta = styled.div`
  margin-top: 1rem;
  color: var(--text-sub);
  font-size: 0.9rem;
  display: flex;
  gap: 1rem;
`;

export const ActionSection = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 1rem;
`;

export const LikeButton = styled.button`
  background: none;
  border: none;
  font-size: 2rem;
  cursor: pointer;
  transition: transform 0.2s;
  
  &:hover {
    transform: scale(1.1);
  }
`;

export const StatusSelect = styled.select`
  padding: 0.5rem;
  border-radius: 6px;
  border: 1px solid var(--border);
  font-weight: 600;
  color: var(--text-main);
  cursor: pointer;
`;

export const ButtonGroup = styled.div`
  display: flex;
  gap: 0.5rem;
`;

export const ActionButton = styled.button`
  padding: 0.5rem 1rem;
  border-radius: 6px;
  border: none;
  font-weight: 600;
  cursor: pointer;
  background-color: ${props => props.$variant === 'danger' ? '#fee2e2' : '#f1f5f9'};
  color: ${props => props.$variant === 'danger' ? '#ef4444' : '#475569'};
  
  &:hover {
    opacity: 0.8;
  }
`;

export const ItemImage = styled.img`
  width: 100%;
  max-height: 500px;
  object-fit: contain;
  background-color: #f8fafc;
  display: block;
`;

export const NoImage = styled.div`
  width: 100%;
  height: 300px;
  background-color: #f1f5f9;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--text-sub);
`;

export const ItemContent = styled.div`
  padding: 2.5rem 2rem;
  font-size: 1.1rem;
  line-height: 1.8;
  white-space: pre-wrap;
  color: var(--text-main);
  border-bottom: 1px solid var(--border);
`;

export const CommentSection = styled.div`
  padding: 2rem;
  background-color: #fafafa;
`;

export const CommentTitle = styled.h3`
  margin-bottom: 1.5rem;
  font-size: 1.2rem;
`;

export const CommentForm = styled.div`
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
`;

export const CommentInput = styled.input`
  flex: 1;
  padding: 0.8rem 1rem;
  border: 1px solid var(--border);
  border-radius: 8px;
  outline: none;
  
  &:focus {
    border-color: var(--primary);
  }
  
  /* [추가] 비활성화 스타일 */
  &:disabled {
    background-color: #f3f4f6;
    cursor: not-allowed;
    color: #94a3b8;
  }
`;

export const CommentButton = styled.button`
  background-color: var(--primary);
  color: white;
  border: none;
  padding: 0 1.5rem;
  border-radius: 8px;
  font-weight: 600;
  cursor: pointer;
  
  &:hover {
    background-color: var(--primary-dark);
  }
  
  /* [추가] 비활성화 스타일 */
  &:disabled {
    background-color: #cbd5e1;
    cursor: not-allowed;
  }
`;

export const CommentList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

export const CommentItem = styled.div`
  background: white;
  padding: 1rem;
  border-radius: 10px;
  border: 1px solid var(--border);
`;

export const CommentHeader = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.5rem;
  font-size: 0.9rem;
`;

export const Writer = styled.span`
  font-weight: 700;
  color: var(--text-main);
`;

export const DateText = styled.span`
  color: var(--text-sub);
`;