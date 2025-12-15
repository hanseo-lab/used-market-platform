package com.kh.board.dto.response;

import com.kh.board.entity.Product;
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
    private String imageUrl;
    private String status;
    private String category;
    private int viewCount;
    private LocalDateTime createdAt;

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.content = product.getContent();
        this.price = product.getPrice();
        this.seller = product.getSeller();
        this.imageUrl = product.getImageUrl();
        this.status = product.getStatus();
        this.category = product.getCategory();
        this.viewCount = product.getViewCount();
        this.createdAt = product.getCreatedAt();
    }
}