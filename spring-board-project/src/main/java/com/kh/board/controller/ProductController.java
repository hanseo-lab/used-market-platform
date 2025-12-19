package com.kh.board.controller;

import com.kh.board.dto.request.ProductRequestDto;
import com.kh.board.dto.request.ProductUpdateDto;
import com.kh.board.dto.request.CommentRequestDto;
import com.kh.board.dto.response.ProductResponseDto;
import com.kh.board.dto.response.CommentResponseDto;
import com.kh.board.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @PageableDefault(size = 12, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ProductResponseDto> page = productService.getProducts(keyword, category, pageable);
        return ResponseEntity.ok(page);
    }

    // 찜한 상품 목록 조회 API
    @GetMapping("/wishlist/my/{memberId}")
    public List<ProductResponseDto> getMyWishlist(@PathVariable Long memberId) {
        return productService.getMemberWishlist(memberId);
    }

    // 상품 상세 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    // 상품 등록 API
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@ModelAttribute ProductRequestDto dto) throws IOException {
        return ResponseEntity.ok(productService.createProduct(dto));
    }

    // 상품 수정 API
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateDto dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    // 상품 삭제 API
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    // 댓글 등록 API
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentResponseDto> addComment(@PathVariable Long id, @RequestBody CommentRequestDto dto) {
        return ResponseEntity.ok(productService.addComment(id, dto));
    }

    // 댓글 목록 조회 API
    @GetMapping("/{id}/comments")
    public List<CommentResponseDto> getReplies(@PathVariable Long id) {
        return productService.getReplies(id);
    }

    // 댓글 수정 API
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto dto) {
        return ResponseEntity.ok(productService.updateComment(commentId, dto.getMemberId(), dto.getContent()));
    }

    // 댓글 삭제 API (memberId는 RequestBody나 Param으로 받아야 함)
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @RequestParam Long memberId) { // 삭제는 Body 대신 Param으로 받는 게 일반적
        productService.deleteComment(commentId, memberId);
        return ResponseEntity.ok().build();
    }

    // 찜 토글 API
    @PostMapping("/{id}/wishlist")
    public ResponseEntity<Boolean> toggleWishlist(@PathVariable Long id, @RequestBody Map<String, Long> body) {
        return ResponseEntity.ok(productService.toggleWishlist(body.get("memberId"), id));
    }

    // 찜 여부 확인 API
    @GetMapping("/{id}/wishlist/{memberId}")
    public ResponseEntity<Boolean> checkWishlist(@PathVariable Long id, @PathVariable Long memberId) {
        return ResponseEntity.ok(productService.isWished(memberId, id));
    }
}