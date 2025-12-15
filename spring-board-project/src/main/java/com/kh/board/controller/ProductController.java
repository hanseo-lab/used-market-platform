package com.kh.board.controller;

import com.kh.board.dto.request.ProductRequestDto;
import com.kh.board.dto.request.ProductUpdateDto;
import com.kh.board.dto.request.ReplyRequestDto;
import com.kh.board.dto.response.ProductResponseDto;
import com.kh.board.dto.response.ReplyResponseDto;
import com.kh.board.service.ProductService;
import lombok.RequiredArgsConstructor;
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
    public List<ProductResponseDto> getProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category) {
        if (keyword != null && !keyword.isBlank()) {
            return productService.searchProducts(keyword);
        } else if (category != null && !category.equals("전체")) {
            return productService.getProductsByCategory(category);
        }
        return productService.getAllProducts();
    }

    // 찜한 상품 목록 조회 API
    @GetMapping("/wishlist/my/{memberId}")
    public List<ProductResponseDto> getMyWishlist(@PathVariable Long memberId) {
        return productService.getMemberWishlist(memberId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@ModelAttribute ProductRequestDto dto) throws IOException {
        return ResponseEntity.ok(productService.createProduct(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateDto dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<ReplyResponseDto> addReply(@PathVariable Long id, @RequestBody ReplyRequestDto dto) {
        return ResponseEntity.ok(productService.addReply(id, dto));
    }

    @GetMapping("/{id}/comments")
    public List<ReplyResponseDto> getReplies(@PathVariable Long id) {
        return productService.getReplies(id);
    }

    @PostMapping("/{id}/wishlist")
    public ResponseEntity<Boolean> toggleWishlist(@PathVariable Long id, @RequestBody Map<String, Long> body) {
        return ResponseEntity.ok(productService.toggleWishlist(body.get("memberId"), id));
    }

    @GetMapping("/{id}/wishlist/{memberId}")
    public ResponseEntity<Boolean> checkWishlist(@PathVariable Long id, @PathVariable Long memberId) {
        return ResponseEntity.ok(productService.isWished(memberId, id));
    }
}