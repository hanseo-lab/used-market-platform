package com.kh.board.repository;

import com.kh.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository  extends JpaRepository<Reply, Long> {
    List<Reply> findByProductIdOrderByCreatedAtDesc(Long productId);
}