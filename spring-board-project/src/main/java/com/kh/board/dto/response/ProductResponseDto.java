package com.kh.board.dto.response;

import com.kh.board.entity.Product;
import com.kh.board.enums.ProductStatus;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
public class ProductResponseDto {
    private Long id;
    private String title;
    private String content;
    private int price;
    private String seller;

    // 응답 시 파일명 정보 제공
    private String originName;
    private String changeName;
    private String image; // 프론트엔드 이미지 태그용 경로 (/images/파일명)

    private ProductStatus status;
    private String category;
    private int viewCount;
    private LocalDateTime createdAt;

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.content = product.getContent();
        this.price = product.getPrice();
        this.seller = product.getSeller();

        this.originName = product.getOriginName();
        this.changeName = product.getChangeName();
        // 저장된 파일명이 있으면 접근 가능한 URL 경로 생성
        if (product.getChangeName() != null) {
            this.image = "/images/" + product.getChangeName();
        }

        this.status = product.getStatus();
        this.category = product.getCategory();
        this.viewCount = product.getViewCount();
        this.createdAt = product.getCreatedAt();
    }
}