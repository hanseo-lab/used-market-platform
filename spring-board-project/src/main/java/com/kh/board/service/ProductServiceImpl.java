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
import com.kh.board.global.exception.ResourceNotFoundException;
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

    private final String uploadDir = "uploads/";

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getProducts(String keyword, String category, Pageable pageable) {
        Page<Product> productPage;

        if (keyword != null && !keyword.isBlank()) {
            productPage = productRepository.findByKeyword(keyword, pageable);
        } else if (category != null && !category.equals("전체")) {
            productPage = productRepository.findByCategory(category, pageable);
        } else {
            productPage = productRepository.findAll(pageable);
        }
        return productPage.map(ProductResponseDto::new);
    }

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
                .orElseThrow(() -> new ResourceNotFoundException("상품이 존재하지 않습니다. id=" + id));

        // [수정 핵심] viewCount가 NULL일 경우 0으로 처리하여 에러 방지
        int currentCount = (product.getViewCount() == null) ? 0 : product.getViewCount();
        product.setViewCount(currentCount + 1);

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
                .viewCount(0) // 초기값 0 명시
                .build();

        return new ProductResponseDto(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductUpdateDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("상품이 존재하지 않습니다. id=" + id));

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
        if (!productRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("삭제할 상품이 존재하지 않습니다.");
        }
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CommentResponseDto addComment(Long productId, CommentRequestDto dto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("상품이 존재하지 않습니다."));
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("회원이 존재하지 않습니다."));

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
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 댓글입니다."));

        if (!comment.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("댓글 수정 권한이 없습니다.");
        }

        comment.changeContent(content);
        return new CommentResponseDto(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, Long memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 댓글입니다."));

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
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("상품 없음"));
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new ResourceNotFoundException("회원 없음"));
            wishlistRepository.save(Wishlist.builder().member(member).product(product).build());
            return true;
        }
    }

    @Override
    public boolean isWished(Long memberId, Long productId) {
        return wishlistRepository.existsByMemberIdAndProductId(memberId, productId);
    }
}