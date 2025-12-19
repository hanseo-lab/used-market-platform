package com.kh.board.repository;

import com.kh.board.entity.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final EntityManager em;

    @Override
    public Comment save(Comment Comment) {
        if (Comment.getId() == null) {
            em.persist(Comment);
        } else {
            em.merge(Comment);
        }
        return Comment;
    }

    @Override
    public List<Comment> findByProductIdOrderByCreatedAtDesc(Long productId) {
        return em.createQuery(
                        "select r from Comment r join fetch r.member where r.product.id = :productId order by r.createdAt desc",
                        Comment.class)
                .setParameter("productId", productId)
                .getResultList();
    }

    @Override
    public void deleteByMemberId(Long memberId) {
        em.createQuery("delete from Comment r where r.member.id = :memberId")
                .setParameter("memberId", memberId)
                .executeUpdate();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public void deleteById(Long id) {
        Comment comment = em.find(Comment.class, id);
        if (comment != null) {
            em.remove(comment);
        }
    }
}