package com.kh.board.repository;

import com.kh.board.entity.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment Comment);
    List<Comment> findByProductIdOrderByCreatedAtDesc(Long productId);
    void deleteByMemberId(Long memberId);
    Optional<Comment> findById(Long id);
    void deleteById(Long id);
}