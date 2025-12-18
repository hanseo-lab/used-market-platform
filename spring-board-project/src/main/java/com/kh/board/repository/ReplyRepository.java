package com.kh.board.repository;

import com.kh.board.entity.Reply;
import java.util.List;

public interface ReplyRepository {
    Reply save(Reply reply);
    List<Reply> findByProductIdOrderByCreatedAtDesc(Long productId);
    void deleteByMemberId(Long memberId);
}