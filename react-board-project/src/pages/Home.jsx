import { useEffect } from 'react'; 
import useItemStore from '../store/itemStore'; 
import { Banner, BannerButton, Container, ItemCard, ItemGrid, ItemImage, ItemInfo, ItemPrice, ItemTitle, Section, SectionTitle, Subtitle, Title } from '../styles/Home.styled';

const HomePage = () => {
  const { items, fetchItems } = useItemStore(); 
  
  // 페이지가 열릴 때 데이터 불러오기
  useEffect(() => {
    fetchItems('', '', 'viewCount,desc', 6);
  }, []);

  // 판매완료(SOLD_OUT)된 상품은 메인에서 제외
  const popularItems = items.filter(item => item.status !== 'SOLD_OUT');

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
        <SectionTitle>지금 가장 인기있는 매물 🔥</SectionTitle>
        {popularItems.length === 0 ? (
          <p style={{color: 'var(--text-sub)'}}>등록된 물품이 없습니다.</p>
        ) : (
          <ItemGrid>
            {popularItems.map(item => (
              <ItemCard key={item.id} to={`/items/${item.id}`}>
                {/* 이미지 컨테이너에 relative 스타일 추가 (뱃지 위치 잡기 위해) */}
                <ItemImage 
                    src={getImageUrl(item.image)} 
                    style={{ position: 'relative' }}
                > 
                  {!item.image && '이미지 없음'}
                  
                  {/* 예약중일 경우 뱃지 표시 */}
                  {item.status === 'RESERVED' && (
                    <span style={{
                        position: 'absolute',
                        top: '10px',
                        right: '10px',
                        backgroundColor: '#ffc107', // 노란색(예약중)
                        color: 'white',
                        padding: '4px 8px',
                        borderRadius: '4px',
                        fontSize: '0.8rem',
                        fontWeight: 'bold',
                        zIndex: 1
                    }}>
                        예약중
                    </span>
                  )}
                </ItemImage>
                
                <ItemInfo>
                  <ItemTitle>{item.title}</ItemTitle>
                  <ItemPrice>{item.price.toLocaleString()}원</ItemPrice>
                  <div style={{ fontSize: '0.85rem', color: '#888', marginTop: '4px' }}>
                    조회 {item.viewCount}
                  </div>
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