package com.kh.board.repository;

import com.kh.board.entity.Wishlist;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WishlistRepositoryImpl implements WishlistRepository {

    private final EntityManager em;

    @Override
    public Wishlist save(Wishlist wishlist) {
        if (wishlist.getId() == null) {
            em.persist(wishlist);
        } else {
            em.merge(wishlist);
        }
        return wishlist;
    }

    @Override
    public void deleteByMemberIdAndProductId(Long memberId, Long productId) {
        em.createQuery("delete from Wishlist w where w.member.id = :memberId and w.product.id = :productId")
                .setParameter("memberId", memberId)
                .setParameter("productId", productId)
                .executeUpdate();
    }

    @Override
    public boolean existsByMemberIdAndProductId(Long memberId, Long productId) {
        Long count = em.createQuery(
                        "select count(w) from Wishlist w where w.member.id = :memberId and w.product.id = :productId",
                        Long.class)
                .setParameter("memberId", memberId)
                .setParameter("productId", productId)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public List<Wishlist> findByMemberIdOrderByIdDesc(Long memberId) {
        return em.createQuery(
                        "select w from Wishlist w join fetch w.product where w.member.id = :memberId order by w.id desc",
                        Wishlist.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public void deleteByMemberId(Long memberId) {
        em.createQuery("delete from Wishlist w where w.member.id = :memberId")
                .setParameter("memberId", memberId)
                .executeUpdate();
    }
}