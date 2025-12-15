import { useEffect } from 'react'; 
import useItemStore from '../store/itemStore'; 
import { Banner, BannerButton, Container, ItemCard, ItemGrid, ItemImage, ItemInfo, ItemPrice, ItemTitle, Section, SectionTitle, Subtitle, Title } from '../styles/Home.styled';

const HomePage = () => {
  // fetchItems 함수도 함께 가져오기
  const { items, fetchItems } = useItemStore(); 
  
  // 페이지가 열릴 때 데이터 불러오기
  useEffect(() => {
    fetchItems(); 
  }, []);

  // 배열의 뒤에서부터 6개를 가져와서 뒤집음 (최신순 6개)
  const recentItems = items.slice(-6).reverse();

  const getImageUrl = (url) => {
    if (!url) return undefined;
    return url.startsWith('http') ? url : `http://localhost:8080${url}`;
  };

  return (
    <Container>
      <Banner>
        <Title>당신의 물건에 가치를 더하세요</Title>
        <Subtitle>이웃과 함께하는 따뜻한 중고거래 플랫폼</Subtitle>
        <BannerButton to="/items">지금 구경하기</BannerButton>
      </Banner>

      <Section>
        <SectionTitle>따끈따끈한 최신 매물 🔥</SectionTitle>
        {recentItems.length === 0 ? (
          <p style={{color: 'var(--text-sub)'}}>등록된 물품이 없습니다.</p>
        ) : (
          <ItemGrid>
            {recentItems.map(item => (
              <ItemCard key={item.id} to={`/items/${item.id}`}>
                <ItemImage src={getImageUrl(item.imageUrl)}> 
                  {!item.imageUrl && '이미지 없음'}
                </ItemImage>
                <ItemInfo>
                  <ItemTitle>{item.title}</ItemTitle>
                  <ItemPrice>{item.price.toLocaleString()}원</ItemPrice>
                </ItemInfo>
              </ItemCard>
            ))}
          </ItemGrid>
        )}
      </Section>
    </Container>
  );
};

export default HomePage;