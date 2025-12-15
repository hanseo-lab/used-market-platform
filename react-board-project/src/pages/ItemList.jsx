import { useEffect, useState } from 'react';
import useItemStore from '../store/itemStore';
import { 
  Container, FilterBar, Title, Controls, Select, SearchInput, SearchButton,
  ItemGrid, ItemCard, ItemImageWrapper, ItemImage, StatusBadge,
  ItemInfo, CategoryTag, ItemTitle, ItemPrice, ItemMeta 
} from '../styles/ItemList.styled';

const CATEGORIES = ["전체", "디지털/가전", "가구/인테리어", "의류", "생활용품", "기타"];

const ItemListPage = () => {
  const { items, fetchItems } = useItemStore();
  const [keyword, setKeyword] = useState('');
  const [category, setCategory] = useState('전체');

  useEffect(() => {
    fetchItems(keyword, category);
  }, [category]);

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
                 <ItemImage src={getImageUrl(item.imageUrl)} />
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