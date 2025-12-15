package com.kh.board.service;

import com.kh.board.dto.request.ProductRequestDto;
import com.kh.board.dto.request.ProductUpdateDto;
import com.kh.board.dto.request.ReplyRequestDto;
import com.kh.board.dto.response.ProductResponseDto;
import com.kh.board.dto.response.ReplyResponseDto;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<ProductResponseDto> getAllProducts();
    List<ProductResponseDto> searchProducts(String keyword);
    List<ProductResponseDto> getProductsByCategory(String category);

    // [추가] 찜 목록 조회
    List<ProductResponseDto> getMemberWishlist(Long memberId);

    ProductResponseDto getProduct(Long id);
    ProductResponseDto createProduct(ProductRequestDto dto) throws IOException;
    ProductResponseDto updateProduct(Long id, ProductUpdateDto dto);
    void deleteProduct(Long id);

    ReplyResponseDto addReply(Long productId, ReplyRequestDto dto);
    List<ReplyResponseDto> getReplies(Long productId);

    boolean toggleWishlist(Long memberId, Long productId);
    boolean isWished(Long memberId, Long productId);
}