package com.kh.board.service;

import com.kh.board.dto.request.ProductRequestDto;
import com.kh.board.dto.request.ProductUpdateDto;
import com.kh.board.dto.request.CommentRequestDto;
import com.kh.board.dto.response.ProductResponseDto;
import com.kh.board.dto.response.CommentResponseDto;
import com.kh.board.entity.Member;
import com.kh.board.entity.Product;
import com.kh.board.entity.Comment;
import com.kh.board.entity.Wishlist;
import com.kh.board.repository.MemberRepository;
import com.kh.board.repository.ProductRepository;
import com.kh.board.repository.CommentRepository;
import com.kh.board.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;
    private final WishlistRepository wishlistRepository;
    private final MemberRepository memberRepository;

    // 파일 저장 경로 (프로젝트 루트/uploads)
    private final String uploadDir = "uploads/";

    // [핵심 변경] 검색, 카테고리, 전체 조회를 통합하여 페이징 처리하는 메서드
    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getProducts(String keyword, String category, Pageable pageable) {
        Page<Product> productPage;

        if (keyword != null && !keyword.isBlank()) {
            // 검색어가 있는 경우
            productPage = productRepository.findByKeyword(keyword, pageable);
        } else if (category != null && !category.equals("전체")) {
            // 카테고리가 선택된 경우
            productPage = productRepository.findByCategory(category, pageable);
        } else {
            // 그 외 (전체 목록)
            productPage = productRepository.findAll(pageable);
        }

        // Entity -> DTO 변환
        return productPage.map(ProductResponseDto::new);
    }

    // 찜 목록 조회 (List 반환 유지)
    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getMemberWishlist(Long memberId) {
        return wishlistRepository.findByMemberIdOrderByIdDesc(memberId).stream()
                .map(wishlist -> new ProductResponseDto(wishlist.getProduct()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductResponseDto getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
        product.setViewCount(product.getViewCount() + 1);
        return new ProductResponseDto(product);
    }

    @Override
    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto dto) throws IOException {
        String originName = null;
        String changeName = null;
        MultipartFile file = dto.getImageFile();

        if (file != null && !file.isEmpty()) {
            String fullPath = System.getProperty("user.dir") + "/" + uploadDir;
            File dir = new File(fullPath);
            if (!dir.exists()) dir.mkdirs();

            originName = file.getOriginalFilename();
            changeName = UUID.randomUUID().toString() + "_" + originName;

            file.transferTo(new File(fullPath + changeName));
        }

        Product product = Product.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .price(dto.getPrice())
                .seller(dto.getSeller())
                .category(dto.getCategory())
                .originName(originName)
                .changeName(changeName)
                .viewCount(0)
                .build();

        return new ProductResponseDto(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductUpdateDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품 없음"));

        String newOriginName = null;
        String newChangeName = null;

        if (dto.getImageFile() != null && !dto.getImageFile().isEmpty()) {
            try {
                String fullPath = System.getProperty("user.dir") + "/" + uploadDir;
                File dir = new File(fullPath);
                if (!dir.exists()) dir.mkdirs();

                newOriginName = dto.getImageFile().getOriginalFilename();
                newChangeName = UUID.randomUUID().toString() + "_" + newOriginName;

                dto.getImageFile().transferTo(new File(fullPath + newChangeName));
            } catch (IOException e) {
                throw new RuntimeException("파일 수정 중 오류 발생", e);
            }
        }

        product.updateProduct(dto, newOriginName, newChangeName);

        return new ProductResponseDto(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CommentResponseDto addComment(Long productId, CommentRequestDto dto) {
        Product product = productRepository.findById(productId).orElseThrow();
        Member member = memberRepository.findById(dto.getMemberId()).orElseThrow();
        Comment comment = Comment.builder()
                .product(product)
                .member(member)
                .content(dto.getContent())
                .build();
        return new CommentResponseDto(commentRepository.save(comment));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getReplies(Long productId) {
        return commentRepository.findByProductIdOrderByCreatedAtDesc(productId)
                .stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentResponseDto updateComment(Long commentId, Long memberId, String content) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        // 본인 확인
        if (!comment.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("댓글 수정 권한이 없습니다.");
        }

        comment.changeContent(content); // Entity에 메서드 추가 필요

        return new CommentResponseDto(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, Long memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        if (!comment.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("댓글 삭제 권한이 없습니다.");
        }

        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public boolean toggleWishlist(Long memberId, Long productId) {
        if (wishlistRepository.existsByMemberIdAndProductId(memberId, productId)) {
            wishlistRepository.deleteByMemberIdAndProductId(memberId, productId);
            return false;
        } else {
            Product product = productRepository.findById(productId).orElseThrow();
            Member member = memberRepository.findById(memberId).orElseThrow();
            wishlistRepository.save(Wishlist.builder().member(member).product(product).build());
            return true;
        }
    }

    @Override
    public boolean isWished(Long memberId, Long productId) {
        return wishlistRepository.existsByMemberIdAndProductId(memberId, productId);
    }
}