import styled from 'styled-components';
import { Link } from 'react-router-dom';

export const Container = styled.div`
  width: 100%;
`;

export const FilterBar = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
  padding: 1rem;
  background-color: var(--surface);
  border-radius: 12px;
  box-shadow: 0 1px 2px rgba(0,0,0,0.05);
  border: 1px solid var(--border);
  
  @media (max-width: 768px) {
    flex-direction: column;
    gap: 1rem;
    align-items: stretch;
  }
`;

export const Title = styled.h2`
  font-size: 1.8rem;
  font-weight: 700;
  color: var(--text-main);
`;

export const Controls = styled.div`
  display: flex;
  gap: 0.8rem;
  align-items: center;
`;

export const SearchInput = styled.input`
  padding: 0.6rem 1rem;
  border: 1px solid var(--border);
  border-radius: 8px;
  width: 250px;
  outline: none;
  transition: border-color 0.2s;
  
  &:focus {
    border-color: var(--primary);
  }
`;

export const SearchButton = styled.button`
  background-color: var(--text-main);
  color: white;
  border: none;
  padding: 0.6rem 1.2rem;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 600;
  
  &:hover {
    background-color: black;
  }
`;

export const Select = styled.select`
  padding: 0.6rem 2rem 0.6rem 1rem;
  border: 1px solid var(--border);
  border-radius: 8px;
  outline: none;
  cursor: pointer;
  background-color: white;
`;

export const ItemGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
  gap: 1.5rem;
`;

export const ItemCard = styled(Link)`
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
  transition: all 0.2s;
  border: 1px solid var(--border);
  position: relative;
  
  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 10px 20px -5px rgba(0,0,0,0.1);
  }
`;

export const ItemImageWrapper = styled.div`
  position: relative;
  padding-top: 75%; /* 4:3 Aspect Ratio */
`;

export const ItemImage = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: #f1f5f9;
  background-image: url(${props => props.src});
  background-size: cover;
  background-position: center;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #94a3b8;
`;

export const StatusBadge = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0,0,0,0.6);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.2rem;
  font-weight: 700;
  backdrop-filter: blur(2px);
  z-index: 10;
`;

export const ItemInfo = styled.div`
  padding: 1rem;
`;

export const CategoryTag = styled.span`
  font-size: 0.75rem;
  color: var(--primary);
  background-color: #e0e7ff;
  padding: 2px 8px;
  border-radius: 4px;
  font-weight: 600;
  display: inline-block;
  margin-bottom: 0.5rem;
`;

export const ItemTitle = styled.h3`
  font-size: 1rem;
  font-weight: 600;
  margin: 0 0 0.5rem 0;
  color: var(--text-main);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

export const ItemPrice = styled.p`
  font-size: 1.1rem;
  font-weight: 700;
  color: var(--text-main);
`;

export const ItemMeta = styled.div`
  margin-top: 0.8rem;
  font-size: 0.8rem;
  color: var(--text-sub);
  display: flex;
  justify-content: space-between;
  align-items: center;
`;