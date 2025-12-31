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

    private String originName;
    private String changeName;
    private String image;

    private ProductStatus status;
    private String category;
    private int viewCount;
    private LocalDateTime createdAt;

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.content = product.getContent();
        this.seller = product.getSeller();

        this.price = (product.getPrice() != null) ? product.getPrice() : 0;
        this.viewCount = (product.getViewCount() != null) ? product.getViewCount() : 0;

        this.originName = product.getOriginName();
        this.changeName = product.getChangeName();

        if (product.getChangeName() != null) {
            this.image = "/images/" + product.getChangeName();
        }

        this.status = product.getStatus();
        this.category = product.getCategory();
        this.createdAt = product.getCreatedAt();
    }
}