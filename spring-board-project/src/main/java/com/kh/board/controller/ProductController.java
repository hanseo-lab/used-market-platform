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
import org.springframework.http.HttpStatus;
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
        ProductResponseDto response = productService.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 상품 수정 API
    // [변경점] PUT -> PATCH로 변경 (부분 수정)
    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @ModelAttribute ProductUpdateDto dto) {
        // @RequestBody 대신 @ModelAttribute를 사용해야 파일 업로드(MultipartFile)와 JSON 필드를 동시에 처리하기 쉽습니다.
        // 만약 JSON으로만 데이터를 받는다면 @RequestBody가 맞지만, ProductUpdateDto에 MultipartFile이 있다면 @ModelAttribute 권장
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
    @PatchMapping("/comments/{commentId}") // 댓글 수정도 PATCH가 의미상 적합하여 변경 제안 (선택사항)
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto dto) {
        return ResponseEntity.ok(productService.updateComment(commentId, dto.getMemberId(), dto.getContent()));
    }

    // 댓글 삭제 API
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @RequestParam Long memberId) {
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