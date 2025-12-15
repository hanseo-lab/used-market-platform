import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import useAuthStore from '../store/authStore'; // Store import
import { Button, Container, Error, Form, FormGroup, Input, Label, LoginLink, Title } from '../styles/Signup.styled';

const SignupPage = () => {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
    confirmPassword: '',
    name: '',
    phone: ''
  });
  const [errors, setErrors] = useState({});
  
  // Zustand 액션 사용
  const signup = useAuthStore((state) => state.signup);
  const navigate = useNavigate();

  const validateForm = () => {
    // ... (기존 유효성 검사 로직 동일) ...
    const newErrors = {};
    if (!formData.email) newErrors.email = '이메일을 입력해주세요.';
    else if (!/\S+@\S+\.\S+/.test(formData.email)) newErrors.email = '올바른 이메일 형식이 아닙니다.';
    if (!formData.password) newErrors.password = '비밀번호를 입력해주세요.';
    else if (formData.password.length < 6) newErrors.password = '비밀번호는 최소 6자 이상이어야 합니다.';
    if (formData.password !== formData.confirmPassword) newErrors.confirmPassword = '비밀번호가 일치하지 않습니다.';
    if (!formData.name) newErrors.name = '이름을 입력해주세요.';
    if (!formData.phone) newErrors.phone = '전화번호를 입력해주세요.';
    return newErrors;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
    if (errors[name]) setErrors(prev => ({ ...prev, [name]: '' }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    const validationErrors = validateForm();
    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      return;
    }

    try {
      const { confirmPassword, ...userData } = formData;
      await signup(userData); // 백엔드 전송
      alert("회원가입 성공! 로그인해주세요.");
      navigate('/login');
    } catch (err) {
      setErrors({ general: err.message });
    }
  };

  return (
    <Container>
      <Title>회원가입</Title>
      <Form onSubmit={handleSubmit}>
         {/* ... 기존 Input UI 코드와 동일 ... */}
         {/* (위 코드 복사해서 그대로 쓰시면 됩니다) */}
         <FormGroup>
          <Label htmlFor="email">이메일 *</Label>
          <Input type="email" id="email" name="email" value={formData.email} onChange={handleChange} placeholder="example@email.com"/>
          {errors.email && <Error>{errors.email}</Error>}
        </FormGroup>
        <FormGroup>
          <Label htmlFor="password">비밀번호 *</Label>
          <Input type="password" id="password" name="password" value={formData.password} onChange={handleChange} placeholder="최소 6자 이상"/>
          {errors.password && <Error>{errors.password}</Error>}
        </FormGroup>
        <FormGroup>
          <Label htmlFor="confirmPassword">비밀번호 확인 *</Label>
          <Input type="password" id="confirmPassword" name="confirmPassword" value={formData.confirmPassword} onChange={handleChange} placeholder="비밀번호를 다시 입력하세요"/>
          {errors.confirmPassword && <Error>{errors.confirmPassword}</Error>}
        </FormGroup>
        <FormGroup>
          <Label htmlFor="name">이름 *</Label>
          <Input type="text" id="name" name="name" value={formData.name} onChange={handleChange} placeholder="홍길동"/>
          {errors.name && <Error>{errors.name}</Error>}
        </FormGroup>
        <FormGroup>
          <Label htmlFor="phone">전화번호 *</Label>
          <Input type="tel" id="phone" name="phone" value={formData.phone} onChange={handleChange} placeholder="010-1234-5678"/>
          {errors.phone && <Error>{errors.phone}</Error>}
        </FormGroup>
        
        {errors.general && <Error>{errors.general}</Error>}
        
        <Button type="submit">가입하기</Button>
      </Form>
      <LoginLink>
        이미 계정이 있으신가요? <Link to="/login">로그인</Link>
      </LoginLink>
    </Container>
  );
};

export default SignupPage;