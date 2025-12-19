package com.kh.board.service;

import com.kh.board.dto.request.*;
import com.kh.board.dto.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.util.List;

public interface ProductService {
    // [통합] 검색, 카테고리, 전체 조회를 모두 담당하는 페이징 메서드
    Page<ProductResponseDto> getProducts(String keyword, String category, Pageable pageable);

    ProductResponseDto getProduct(Long id);
    ProductResponseDto createProduct(ProductRequestDto dto) throws IOException;
    ProductResponseDto updateProduct(Long id, ProductUpdateDto dto);
    void deleteProduct(Long id);

    CommentResponseDto addComment(Long productId, CommentRequestDto dto);
    List<CommentResponseDto> getReplies(Long productId);
    CommentResponseDto updateComment(Long commentId, Long memberId, String content);
    void deleteComment(Long commentId, Long memberId);

    boolean toggleWishlist(Long memberId, Long productId);
    boolean isWished(Long memberId, Long productId);
    List<ProductResponseDto> getMemberWishlist(Long memberId);
}