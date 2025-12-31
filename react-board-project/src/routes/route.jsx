import { Routes, Route } from 'react-router-dom';
import Layout from '../components/Layout';
import ProtectedRoute from '../components/ProtectedRoute';
import HomePage from '../pages/Home';
import LoginPage from '../pages/Login';
import SignupPage from '../pages/Signup';
import ItemListPage from '../pages/ItemList';
import ItemDetailPage from '../pages/ItemDetail';
import ItemFormPage from '../pages/ItemForm';
import MyPage from '../pages/Mypage';
import NotFoundPage from '../pages/NotFound';

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<HomePage />} />
        <Route path="login" element={<LoginPage />} />
        <Route path="signup" element={<SignupPage />} />
        <Route path="items" element={<ItemListPage />} />
        <Route path="items/:id" element={<ItemDetailPage />} />
        <Route 
          path="items/new" 
          element={
            <ProtectedRoute>
              <ItemFormPage />
            </ProtectedRoute>
          } 
        />
        <Route 
          path="items/edit/:id" 
          element={
            <ProtectedRoute>
              <ItemFormPage />
            </ProtectedRoute>
          } 
        />
        <Route 
          path="mypage" 
          element={
            <ProtectedRoute>
              <MyPage />
            </ProtectedRoute>
          } 
        />
        <Route path="*" element={<NotFoundPage />} />
      </Route>
    </Routes>
  );
};

export default AppRoutes;