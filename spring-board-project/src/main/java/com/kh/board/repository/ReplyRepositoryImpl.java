package com.kh.board.repository;

import com.kh.board.entity.Reply;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReplyRepositoryImpl implements ReplyRepository {

    private final EntityManager em;

    @Override
    public Reply save(Reply reply) {
        if (reply.getId() == null) {
            em.persist(reply);
        } else {
            em.merge(reply);
        }
        return reply;
    }

    @Override
    public List<Reply> findByProductIdOrderByCreatedAtDesc(Long productId) {
        return em.createQuery(
                        "select r from Reply r join fetch r.member where r.product.id = :productId order by r.createdAt desc",
                        Reply.class)
                .setParameter("productId", productId)
                .getResultList();
    }

    @Override
    public void deleteByMemberId(Long memberId) {
        em.createQuery("delete from Reply r where r.member.id = :memberId")
                .setParameter("memberId", memberId)
                .executeUpdate();
    }
}