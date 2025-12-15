import { Container, ErrorCode, ErrorDescription, ErrorMessage, HomeButton } from "../styles/NotFound.styled";

const NotFoundPage = () => {
  return (
    <Container>
      <ErrorCode>404</ErrorCode>
      <ErrorMessage>페이지를 찾을 수 없습니다</ErrorMessage>
      <ErrorDescription>
        요청하신 페이지가 존재하지 않거나 이동되었습니다.
      </ErrorDescription>
      <HomeButton to="/">홈으로 돌아가기</HomeButton>
    </Container>
  );
};

export default NotFoundPage;