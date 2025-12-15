import { useState, useEffect } from 'react';
import useAuthStore from '../store/authStore';
import useItemStore from '../store/itemStore';
import axios from 'axios';
import { 
    Button, Container, DeleteButton, EmptyState, Form, FormGroup, Input, 
    ItemActions, ItemCard, ItemImage, ItemInfo, ItemList, ItemPrice, 
    ItemTitle, Label, Section, SectionTitle, StyledLink, Title 
} from '../styles/Mypage.styled';

const MyPage = () => {
  const { user } = useAuthStore(); 
  const { items, fetchItems, deleteItem } = useItemStore(); // fetchItems 필요

  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState({
    address: user?.address || '',
    password: ''
  });

  // 찜 목록 상태
  const [wishlist, setWishlist] = useState([]);

  // 데이터 로딩
  useEffect(() => {
    if (user) {
        fetchItems(); // 전체 아이템 로드 (내 판매 물품 필터용)
        fetchWishlist(); // 찜 목록 로드
    }
  }, [user]);

  // [추가] 찜 목록 불러오기 함수
  const fetchWishlist = async () => {
      try {
          const res = await axios.get(`/api/products/wishlist/my/${user.id}`);
          setWishlist(res.data);
      } catch (e) {
          console.error("찜 목록 로딩 실패", e);
      }
  };

  const myItems = items.filter(item => item.seller === user?.name);

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
    if (isEditing) {
      handleUpdate();
    } else {
      setIsEditing(true);
      setFormData(prev => ({ ...prev, address: user.address || '' }));
    }
  };

  const handleDelete = (itemId) => {
    if (window.confirm('정말 삭제하시겠습니까?')) {
      deleteItem(itemId);
    }
  };

  const getImageUrl = (url) => url ? (url.startsWith('http') ? url : `http://localhost:8080${url}`) : null;

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

      {/* 2. 관심 상품 (찜 목록) 섹션 */}
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
    </Container>
  );
};

export default MyPage;