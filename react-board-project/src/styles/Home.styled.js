import styled from "styled-components";
import { Link } from 'react-router-dom';

export const Container = styled.div`
  width: 100%;
`;

export const Banner = styled.div`
  background: linear-gradient(135deg, var(--primary) 0%, #818cf8 100%);
  color: white;
  padding: 5rem 2rem;
  border-radius: 20px;
  text-align: center;
  margin-bottom: 4rem;
  box-shadow: 0 10px 25px -5px rgba(79, 70, 229, 0.4);
`;

export const Title = styled.h1`
  font-size: 3rem;
  font-weight: 800;
  margin-bottom: 1rem;
  line-height: 1.2;
`;

export const Subtitle = styled.p`
  font-size: 1.25rem;
  margin-bottom: 2.5rem;
  opacity: 0.9;
`;

export const BannerButton = styled(Link)`
  display: inline-block;
  background-color: white;
  color: var(--primary);
  padding: 1rem 2.5rem;
  border-radius: 50px;
  font-weight: 700;
  font-size: 1.1rem;
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
  transition: transform 0.2s, box-shadow 0.2s;
  
  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 12px rgba(0,0,0,0.15);
  }
`;

export const Section = styled.section`
  margin-bottom: 4rem;
`;

export const SectionTitle = styled.h2`
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 2rem;
  color: var(--text-main);
  position: relative;
  display: inline-block;
  
  &::after {
    content: '';
    position: absolute;
    bottom: -5px;
    left: 0;
    width: 40%;
    height: 4px;
    background-color: var(--primary);
    border-radius: 2px;
  }
`;

export const ItemGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));
  gap: 2rem;
`;

export const ItemCard = styled(Link)`
  background: var(--surface);
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05);
  transition: transform 0.2s, box-shadow 0.2s;
  border: 1px solid var(--border);
  
  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
  }
`;

export const ItemImage = styled.div`
  width: 100%;
  height: 220px;
  background-color: #f1f5f9;
  background-image: url(${props => props.src});
  background-size: cover;
  background-position: center;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #94a3b8;
`;

export const ItemInfo = styled.div`
  padding: 1.25rem;
`;

export const ItemTitle = styled.h3`
  font-size: 1.1rem;
  font-weight: 600;
  margin-bottom: 0.5rem;
  color: var(--text-main);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

export const ItemPrice = styled.p`
  font-size: 1.2rem;
  font-weight: 700;
  color: var(--primary);
`;