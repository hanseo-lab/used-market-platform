import { Outlet, useNavigate } from 'react-router-dom';
import useAuthStore from '../store/authStore';
import { Footer, HeaderContainer, HeaderContent, Logo, Main, Nav, NavLink } from '../styles/Layout.styled';

const Layout = () => {
  const { user, logout } = useAuthStore();
  const navigate = useNavigate();

  // 매물보기 클릭 시 검색 조건을 초기화하라는 신호를 보냄
  const handleItemsClick = (e) => {
    e.preventDefault();
    navigate('/items', { state: { reset: true } });
  };

  return (
    <>
      <HeaderContainer>
        <HeaderContent>
          <Logo to="/">중고마켓</Logo>
          <Nav>
            <NavLink to="/items" onClick={handleItemsClick}>매물보기</NavLink>
            {user ? (
              <>
                <NavLink to="/items/new">판매하기</NavLink>
                <NavLink to="/mypage">마이페이지</NavLink>
                <NavLink as="button" onClick={logout} style={{background:'none', border:'none', cursor:'pointer'}}>
                  로그아웃
                </NavLink>
              </>
            ) : (
              <>
                <NavLink to="/login">로그인</NavLink>
                <NavLink to="/signup">회원가입</NavLink>
              </>
            )}
          </Nav>
        </HeaderContent>
      </HeaderContainer>
      
      <Main>
        <Outlet />
      </Main>

      <Footer>
        © 2025 Used Market Project. All rights reserved.
      </Footer>
    </>
  );
};

export default Layout;