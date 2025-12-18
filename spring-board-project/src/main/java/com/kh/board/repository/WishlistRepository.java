package com.kh.board.repository;

import com.kh.board.entity.Wishlist;
import java.util.List;

public interface WishlistRepository {
    Wishlist save(Wishlist wishlist);
    void deleteByMemberIdAndProductId(Long memberId, Long productId);
    boolean existsByMemberIdAndProductId(Long memberId, Long productId);
    List<Wishlist> findByMemberIdOrderByIdDesc(Long memberId);
    void deleteByMemberId(Long memberId);
}