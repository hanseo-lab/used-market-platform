package com.kh.board.service;

import com.kh.board.dto.request.ProductRequestDto;
import com.kh.board.dto.request.ProductUpdateDto;
import com.kh.board.dto.request.ReplyRequestDto;
import com.kh.board.dto.response.ProductResponseDto;
import com.kh.board.dto.response.ReplyResponseDto;
import com.kh.board.entity.Member;
import com.kh.board.entity.Product;
import com.kh.board.entity.Reply;
import com.kh.board.entity.Wishlist;
import com.kh.board.repository.MemberRepository;
import com.kh.board.repository.ProductRepository;
import com.kh.board.repository.ReplyRepository;
import com.kh.board.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
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
    private final ReplyRepository replyRepository;
    private final WishlistRepository wishlistRepository;
    private final MemberRepository memberRepository;

    // 파일 저장 경로 (프로젝트 루트/uploads)
    private final String uploadDir = "uploads/";

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> searchProducts(String keyword) {
        return productRepository.findByTitleContainingOrContentContainingOrderByCreatedAtDesc(keyword, keyword)
                .stream().map(ProductResponseDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProductsByCategory(String category) {
        return productRepository.findByCategoryOrderByCreatedAtDesc(category)
                .stream().map(ProductResponseDto::new).collect(Collectors.toList());
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
                .originName(originName) // 원본명 저장
                .changeName(changeName) // 변경명 저장
                // status는 기본값(FOR_SALE) 사용
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

        // 수정 시 새 파일이 들어왔는지 확인
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

        // 엔티티의 수정 메서드 호출 (파일 변경 없으면 null 전달됨)
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
    public ReplyResponseDto addReply(Long productId, ReplyRequestDto dto) {
        Product product = productRepository.findById(productId).orElseThrow();
        Member member = memberRepository.findById(dto.getMemberId()).orElseThrow();
        Reply reply = Reply.builder()
                .product(product)
                .member(member)
                .content(dto.getContent())
                .build();
        return new ReplyResponseDto(replyRepository.save(reply));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReplyResponseDto> getReplies(Long productId) {
        return replyRepository.findByProductIdOrderByCreatedAtDesc(productId)
                .stream().map(ReplyResponseDto::new).collect(Collectors.toList());
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