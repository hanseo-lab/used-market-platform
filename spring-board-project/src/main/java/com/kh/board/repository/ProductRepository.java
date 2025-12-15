package com.kh.board.repository;

import com.kh.board.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // 최신순 전체 조회
    List<Product> findAllByOrderByCreatedAtDesc();

    // 검색 (제목 또는 내용에 키워드가 포함된 경우)
    List<Product> findByTitleContainingOrContentContainingOrderByCreatedAtDesc(String title, String content);

    // 카테고리별 조회
    List<Product> findByCategoryOrderByCreatedAtDesc(String category);
}