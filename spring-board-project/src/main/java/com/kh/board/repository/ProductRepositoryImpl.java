package com.kh.board.repository;

import com.kh.board.entity.Product;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final EntityManager em;

    @Override
    public Product save(Product product) {
        if (product.getId() == null) em.persist(product);
        else em.merge(product);
        return product;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(em.find(Product.class, id));
    }

    @Override
    public void deleteById(Long id) {
        Product product = em.find(Product.class, id);
        if (product != null) em.remove(product);
    }

    @Override
    public List<Product> findBySeller(String seller) {
        return em.createQuery("select p from Product p where p.seller = :seller", Product.class)
                .setParameter("seller", seller)
                .getResultList();
    }

    // --- [여기서부터 페이징 메서드 구현] ---

    // 1. 전체 목록 페이징
    @Override
    public Page<Product> findAll(Pageable pageable) {
        Long totalCount = em.createQuery("select count(p) from Product p", Long.class).getSingleResult();
        List<Product> products = em.createQuery("select p from Product p order by p.createdAt desc", Product.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return new PageImpl<>(products, pageable, totalCount);
    }

    // 2. 검색어 페이징
    @Override
    public Page<Product> findByKeyword(String keyword, Pageable pageable) {
        Long totalCount = em.createQuery(
                        "select count(p) from Product p where p.title like :kw or p.content like :kw", Long.class)
                .setParameter("kw", "%" + keyword + "%")
                .getSingleResult();

        List<Product> products = em.createQuery(
                        "select p from Product p where p.title like :kw or p.content like :kw order by p.createdAt desc", Product.class)
                .setParameter("kw", "%" + keyword + "%")
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(products, pageable, totalCount);
    }

    // 3. 카테고리 페이징
    @Override
    public Page<Product> findByCategory(String category, Pageable pageable) {
        Long totalCount = em.createQuery(
                        "select count(p) from Product p where p.category = :category", Long.class)
                .setParameter("category", category)
                .getSingleResult();

        List<Product> products = em.createQuery(
                        "select p from Product p where p.category = :category order by p.createdAt desc", Product.class)
                .setParameter("category", category)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(products, pageable, totalCount);
    }
}