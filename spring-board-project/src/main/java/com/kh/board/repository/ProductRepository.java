package com.kh.board.repository;

import com.kh.board.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    void deleteById(Long id);

    // [중요] 모든 조회 메서드를 Page<Product> 반환으로 통일합니다.
    Page<Product> findAll(Pageable pageable);
    Page<Product> findByKeyword(String keyword, Pageable pageable);
    Page<Product> findByCategory(String category, Pageable pageable);

    // 판매자 조회 등은 리스트로 유지해도 됩니다 (필요시 페이징 변경 가능)
    java.util.List<Product> findBySeller(String seller);
}