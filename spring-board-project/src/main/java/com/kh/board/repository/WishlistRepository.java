package com.kh.board.repository;

import com.kh.board.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    boolean existsByMemberIdAndProductId(Long memberId, Long productId);
    void deleteByMemberIdAndProductId(Long memberId, Long productId);

    // 내 찜 목록 조회 (최신순)
    List<Wishlist> findByMemberIdOrderByIdDesc(Long memberId);
}