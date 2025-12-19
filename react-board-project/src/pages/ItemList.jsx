import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import useItemStore from '../store/itemStore';
import { 
  Container, FilterBar, Title, Controls, Select, SearchInput, SearchButton,
  ItemGrid, ItemCard, ItemImageWrapper, ItemImage, StatusBadge,
  ItemInfo, CategoryTag, ItemTitle, ItemPrice, ItemMeta 
} from '../styles/ItemList.styled';

const CATEGORIES = ["전체", "디지털/가전", "가구/인테리어", "의류", "생활용품", "기타"];

const ItemListPage = () => {
  const { items, fetchItems } = useItemStore();
  const location = useLocation(); // 리셋 신호 감지용

  const [keyword, setKeyword] = useState('');
  const [category, setCategory] = useState('전체');

  // [1] 헤더에서 '매물보기'를 클릭해서 들어왔을 때 (리셋 신호 감지)
  useEffect(() => {
    if (location.state?.reset) {
      setKeyword('');
      setCategory('전체');
      fetchItems('', '전체'); // 초기화된 상태로 조회
      
      // 상태를 소비했으므로 히스토리에서 state 제거 (새로고침 시 중복 실행 방지)
      window.history.replaceState({}, document.title);
    }
  }, [location.state, fetchItems]);

  // [2] 카테고리가 변경될 때마다 조회 (리셋 신호가 없을 때만 동작)
  useEffect(() => {
    if (!location.state?.reset) {
        fetchItems(keyword, category);
    }
  }, [category]); // fetchItems나 keyword는 의존성에서 제외하여 불필요한 호출 방지

  const handleSearch = (e) => {
    e.preventDefault();
    fetchItems(keyword, category);
  };

  const getImageUrl = (url) => {
    if (!url) return null;
    return url.startsWith('http') ? url : `http://localhost:8080${url}`;
  };

  return (
    <Container>
      <FilterBar>
        <Title>중고 매물</Title>
        <Controls>
            <Select 
                value={category} 
                onChange={(e) => setCategory(e.target.value)}
            >
                {CATEGORIES.map(c => <option key={c} value={c}>{c}</option>)}
            </Select>
            <form onSubmit={handleSearch} style={{display:'flex', gap:'8px'}}>
                <SearchInput 
                    type="text" 
                    placeholder="어떤 물건을 찾으세요?" 
                    value={keyword}
                    onChange={(e) => setKeyword(e.target.value)}
                />
                <SearchButton type="submit">검색</SearchButton>
            </form>
        </Controls>
      </FilterBar>

      <ItemGrid>
        {items.length === 0 ? <p style={{gridColumn:'1/-1', textAlign:'center', padding:'3rem'}}>등록된 물품이 없습니다.</p> : items.map((item) => (
          <ItemCard key={item.id} to={`/items/${item.id}`}>
              <ItemImageWrapper>
                 {/* [수정] item.imageUrl -> item.image 로 변경 */}
                 <ItemImage src={getImageUrl(item.image)} />
                 {item.status !== 'FOR_SALE' && (
                     <StatusBadge>
                         {item.status === 'RESERVED' ? '예약중' : '판매완료'}
                     </StatusBadge>
                 )}
              </ItemImageWrapper>
              <ItemInfo>
                <CategoryTag>{item.category || '기타'}</CategoryTag>
                <ItemTitle>{item.title}</ItemTitle>
                <ItemPrice>{item.price.toLocaleString()}원</ItemPrice>
                <ItemMeta>
                   <span>{item.seller}</span>
                   <span>조회 {item.viewCount}</span>
                </ItemMeta>
              </ItemInfo>
          </ItemCard>
        ))}
      </ItemGrid>
    </Container>
  );
};

export default ItemListPage;