import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import useAuthStore from '../store/authStore';
import { Button, Container, Form, FormGroup, Input, Label, Textarea, Title, Select, RequiredMark, BackButton } from '../styles/ItemForm.styled';

const CATEGORIES = ["디지털/가전", "가구/인테리어", "의류", "생활용품", "기타"];

const ItemFormPage = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const user = useAuthStore((state) => state.user);
  
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [price, setPrice] = useState('');
  const [category, setCategory] = useState('기타'); 
  const [imageFile, setImageFile] = useState(null);

  useEffect(() => {
    if (id) {
      axios.get(`/api/products/${id}`)
        .then(res => {
          const data = res.data;
          setTitle(data.title);
          setContent(data.content);
          setPrice(data.price);
          setCategory(data.category || '기타');
        })
        .catch(err => {
          alert("상품 정보를 불러오지 못했습니다.");
          navigate('/items');
        });
    }
  }, [id, navigate]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    // 브라우저 required 속성이 1차로 막아주지만, 이중 체크
    if (!title || !content || !price) {
      alert('필수 항목을 모두 입력해주세요.');
      return;
    }

    try {
      if (id) {
        await axios.put(`/api/products/${id}`, {
          title, content, price: parseInt(price), 
          category, status: 'FOR_SALE'
        });
        alert('수정되었습니다.');
      } else {
        const formData = new FormData();
        formData.append('title', title);
        formData.append('content', content);
        formData.append('price', price);
        formData.append('category', category);
        formData.append('seller', user?.name || '익명');
        if (imageFile) formData.append('imageFile', imageFile);

        await axios.post('/api/products', formData);
        alert('등록되었습니다!');
      }
      navigate('/items');
    } catch (error) {
      console.error(error);
      alert('오류 발생');
    }
  };

  return (
    <Container>
      <BackButton to="/items">← 목록으로</BackButton>
      <Title>{id ? '상품 수정' : '상품 등록'}</Title>
      <Form onSubmit={handleSubmit}>
        <FormGroup>
          <Label>카테고리 <RequiredMark>*</RequiredMark></Label>
          <Select 
            value={category} 
            onChange={(e) => setCategory(e.target.value)}
          >
            {CATEGORIES.map(c => <option key={c} value={c}>{c}</option>)}
          </Select>
        </FormGroup>

        <FormGroup>
          <Label>제목 <RequiredMark>*</RequiredMark></Label>
          <Input 
            value={title} 
            onChange={(e) => setTitle(e.target.value)} 
            placeholder="상품 제목을 입력하세요" 
            required 
          />
        </FormGroup>

        {!id && (
            <FormGroup>
                <Label>사진</Label>
                {/* 사진은 필수가 아니면 RequiredMark 생략 */}
                <Input type="file" accept="image/*" onChange={(e)=>setImageFile(e.target.files[0])} />
            </FormGroup>
        )}

        <FormGroup>
          <Label>가격 <RequiredMark>*</RequiredMark></Label>
          <Input 
            type="number" 
            value={price} 
            onChange={(e) => setPrice(e.target.value)} 
            placeholder="가격을 입력하세요 (숫자만)" 
            required 
          />
        </FormGroup>

        <FormGroup>
          <Label>내용 <RequiredMark>*</RequiredMark></Label>
          <Textarea 
            value={content} 
            onChange={(e) => setContent(e.target.value)} 
            placeholder="상품에 대한 자세한 설명을 적어주세요" 
            rows="5" 
            required 
          />
        </FormGroup>

        <Button type="submit">{id ? '수정완료' : '등록하기'}</Button>
      </Form>
    </Container>
  );
};

export default ItemFormPage;