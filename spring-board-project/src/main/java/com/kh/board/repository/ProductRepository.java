package com.kh.board.repository;

import com.kh.board.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    void deleteById(Long id);

    List<Product> findAllByOrderByCreatedAtDesc();
    List<Product> findByTitleContainingOrContentContainingOrderByCreatedAtDesc(String title, String content);
    List<Product> findByCategoryOrderByCreatedAtDesc(String category);
    List<Product> findBySeller(String seller);
}