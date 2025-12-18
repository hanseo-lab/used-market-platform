package com.kh.board.repository;

import com.kh.board.entity.Product;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
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
    public List<Product> findAllByOrderByCreatedAtDesc() {
        return em.createQuery("select p from Product p order by p.createdAt desc", Product.class).getResultList();
    }

    @Override
    public List<Product> findByTitleContainingOrContentContainingOrderByCreatedAtDesc(String title, String content) {
        return em.createQuery("select p from Product p where p.title like :keyword or p.content like :keyword order by p.createdAt desc", Product.class)
                .setParameter("keyword", "%" + title + "%")
                .getResultList();
    }

    @Override
    public List<Product> findByCategoryOrderByCreatedAtDesc(String category) {
        return em.createQuery("select p from Product p where p.category = :category order by p.createdAt desc", Product.class)
                .setParameter("category", category)
                .getResultList();
    }

    @Override
    public List<Product> findBySeller(String seller) {
        return em.createQuery("select p from Product p where p.seller = :seller", Product.class)
                .setParameter("seller", seller)
                .getResultList();
    }
}