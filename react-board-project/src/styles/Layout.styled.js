import styled from 'styled-components';
import { Link } from 'react-router-dom';

export const HeaderContainer = styled.header`
  background-color: var(--surface);
  border-bottom: 1px solid var(--border);
  position: sticky;
  top: 0;
  z-index: 100;
  box-shadow: 0 1px 3px rgba(0,0,0,0.05);
`;

export const HeaderContent = styled.div`
  max-width: 1200px;
  margin: 0 auto;
  padding: 1rem 1.5rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

export const Logo = styled(Link)`
  font-size: 1.5rem;
  font-weight: 800;
  color: var(--primary);
  letter-spacing: -0.5px;
`;

export const Nav = styled.nav`
  display: flex;
  gap: 1.5rem;
  align-items: center;
`;

export const NavLink = styled(Link)`
  font-weight: 500;
  color: var(--text-sub);
  transition: color 0.2s;

  &:hover {
    color: var(--primary);
  }
`;

export const Main = styled.main`
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem 1.5rem;
  min-height: calc(100vh - 160px);
`;

export const Footer = styled.footer`
  background-color: var(--surface);
  border-top: 1px solid var(--border);
  padding: 2rem;
  text-align: center;
  color: var(--text-sub);
  margin-top: auto;
`;