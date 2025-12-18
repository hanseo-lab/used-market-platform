import { useState, useEffect } from 'react';
import useAuthStore from '../store/authStore';
import useItemStore from '../store/itemStore';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { 
    Button, Container, DeleteButton, EmptyState, Form, FormGroup, Input, 
    ItemActions, ItemCard, ItemImage, ItemInfo, ItemList, ItemPrice, 
    ItemTitle, Label, Section, SectionTitle, StyledLink, Title, WithdrawButton,
    ModalOverlay, ModalContent, ModalTitle, ModalActions // 모달 관련 스타일 추가 import
} from '../styles/Mypage.styled';

const MyPage = () => {
  const { user, deleteAccount } = useAuthStore();
  const { items, fetchItems, deleteItem } = useItemStore();
  const navigate = useNavigate();

  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState({
    address: user?.address || '',
    password: ''
  });

  const [wishlist, setWishlist] = useState([]);
  
  // [추가] 모달 상태 및 입력 비밀번호
  const [showWithdrawModal, setShowWithdrawModal] = useState(false);
  const [withdrawPassword, setWithdrawPassword] = useState('');

  useEffect(() => {
    if (user) {
        fetchItems();
        fetchWishlist();
    }
  }, [user]);

  const fetchWishlist = async () => {
      try {
          const res = await axios.get(`/api/products/wishlist/my/${user.id}`);
          setWishlist(res.data);
      } catch (e) {
          console.error("찜 목록 로딩 실패", e);
      }
  };

  const myItems = items.filter(item => item.seller === user?.name);

  // ... (handleUpdate, toggleEdit, handleDelete 등 기존 로직 유지) ...
  const handleUpdate = async () => {
    try {
      const res = await axios.put(`/api/members/${user.id}`, {
        address: formData.address,
        password: formData.password || undefined
      });
      alert("정보가 성공적으로 수정되었습니다.");
      const updatedUser = res.data;
      localStorage.setItem('user', JSON.stringify(updatedUser));
      window.location.reload(); 
    } catch (e) {
      alert("정보 수정에 실패했습니다.");
    }
  };

  const toggleEdit = () => {
    if (isEditing) { handleUpdate(); } else { setIsEditing(true); setFormData(prev => ({ ...prev, address: user.address || '' })); }
  };

  const handleDelete = (itemId) => {
    if (window.confirm('정말 삭제하시겠습니까?')) { deleteItem(itemId); }
  };

  const getImageUrl = (url) => url ? (url.startsWith('http') ? url : `http://localhost:8080${url}`) : null;

  // [수정] 탈퇴 버튼 클릭 시 모달 열기
  const handleWithdrawClick = () => {
    setShowWithdrawModal(true);
    setWithdrawPassword('');
  };

  // [추가] 모달에서 '탈퇴확인' 눌렀을 때 실행될 로직
  const confirmWithdraw = async () => {
      if (!withdrawPassword) {
          alert("비밀번호를 입력해주세요.");
          return;
      }
      
      try {
          await deleteAccount(withdrawPassword); // 비밀번호 전달
          alert("회원 탈퇴가 완료되었습니다. 이용해 주셔서 감사합니다.");
          setShowWithdrawModal(false);
          navigate('/');
      } catch (e) {
          alert(e.message || "탈퇴 처리에 실패했습니다. 비밀번호를 확인해주세요.");
      }
  };

  if (!user) return <Container>로그인이 필요합니다.</Container>;

  return (
    <Container>
      <Title>마이페이지</Title>
      
      {/* 1. 내 정보 섹션 */}
      <Section>
        <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>
           <SectionTitle>내 정보</SectionTitle>
           <Button onClick={toggleEdit}>{isEditing ? '저장 완료' : '정보 수정'}</Button>
        </div>
        <Form>
          <FormGroup>
            <Label>이메일</Label>
            <Input value={user.email} disabled />
          </FormGroup>
          <FormGroup>
            <Label>이름</Label>
            <Input value={user.name} disabled />
          </FormGroup>
          <FormGroup>
            <Label>주소</Label>
            <Input 
              value={isEditing ? formData.address : (user.address || '등록된 주소가 없습니다.')} 
              disabled={!isEditing}
              placeholder="주소를 입력하세요"
              onChange={(e) => setFormData({...formData, address: e.target.value})}
            />
          </FormGroup>
          {isEditing && (
             <FormGroup>
               <Label>새 비밀번호</Label>
               <Input 
                 type="password" 
                 placeholder="변경할 비밀번호를 입력하세요 (비워두면 유지)"
                 value={formData.password}
                 onChange={(e) => setFormData({...formData, password: e.target.value})}
               />
             </FormGroup>
          )}
        </Form>
      </Section>

      {/* 2. 관심 상품 섹션 */}
      <Section>
        <SectionTitle>관심 상품 ❤️ ({wishlist.length})</SectionTitle>
        {wishlist.length === 0 ? (
          <EmptyState>찜한 상품이 없습니다.</EmptyState>
        ) : (
          <ItemList>
            {wishlist.map(item => (
              <ItemCard key={item.id}>
                <ItemImage src={getImageUrl(item.imageUrl)} />
                <ItemInfo>
                    <ItemTitle>{item.title}</ItemTitle>
                    <ItemPrice>{item.price.toLocaleString()}원</ItemPrice>
                    <ItemActions>
                        <StyledLink to={`/items/${item.id}`}>상세보기</StyledLink>
                    </ItemActions>
                </ItemInfo>
              </ItemCard>
            ))}
          </ItemList>
        )}
      </Section>

      {/* 3. 내 판매 물품 섹션 */}
      <Section>
        <SectionTitle>내 판매 물품 ({myItems.length})</SectionTitle>
        {myItems.length === 0 ? (
          <EmptyState>등록한 물품이 없습니다.</EmptyState>
        ) : (
          <ItemList>
            {myItems.map(item => (
              <ItemCard key={item.id}>
                <ItemImage src={getImageUrl(item.imageUrl)} />
                <ItemInfo>
                    <ItemTitle>{item.title}</ItemTitle>
                    <ItemPrice>{item.price.toLocaleString()}원</ItemPrice>
                    <ItemActions>
                        <StyledLink to={`/items/${item.id}`}>상세보기</StyledLink>
                        <DeleteButton onClick={() => handleDelete(item.id)}>삭제</DeleteButton>
                    </ItemActions>
                </ItemInfo>
              </ItemCard>
            ))}
          </ItemList>
        )}
      </Section>

      {/* 4. 하단 탈퇴 버튼 */}
      <div style={{ display: 'flex', justifyContent: 'flex-end', marginBottom: '3rem' }}>
        <WithdrawButton onClick={handleWithdrawClick}>회원 탈퇴</WithdrawButton>
      </div>

      {/* [추가] 탈퇴 확인 모달 */}
      {showWithdrawModal && (
          <ModalOverlay onClick={() => setShowWithdrawModal(false)}>
              <ModalContent onClick={(e) => e.stopPropagation()}>
                  <ModalTitle>회원 탈퇴 확인</ModalTitle>
                  <p>정말로 탈퇴하시겠습니까? 탈퇴 시 작성한 게시글과 찜 목록이 모두 삭제되며 복구할 수 없습니다.</p>
                  <FormGroup>
                      <Label>비밀번호 확인</Label>
                      <Input 
                          type="password" 
                          placeholder="비밀번호를 입력하세요"
                          value={withdrawPassword}
                          onChange={(e) => setWithdrawPassword(e.target.value)}
                          autoFocus
                      />
                  </FormGroup>
                  <ModalActions>
                      <Button $variant="secondary" onClick={() => setShowWithdrawModal(false)}>취소</Button>
                      <Button style={{backgroundColor: '#dc3545'}} onClick={confirmWithdraw}>탈퇴하기</Button>
                  </ModalActions>
              </ModalContent>
          </ModalOverlay>
      )}

    </Container>
  );
};

export default MyPage;