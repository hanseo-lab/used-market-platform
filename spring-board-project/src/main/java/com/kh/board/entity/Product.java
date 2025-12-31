package com.kh.board.entity;

import com.kh.board.dto.request.ProductUpdateDto;
import com.kh.board.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String content;

    private Integer price; // Integer 확인

    private String seller;

    private String originName;
    private String changeName;

    private String category;

    @ColumnDefault("0")
    @Builder.Default
    private Integer viewCount = 0; // Integer 확인

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @ColumnDefault("'FOR_SALE'")
    @Builder.Default
    private ProductStatus status = ProductStatus.FOR_SALE;

    public void updateProduct(ProductUpdateDto dto, String newOriginName, String newChangeName) {
        if (dto.getTitle() != null) this.title = dto.getTitle();
        if (dto.getContent() != null) this.content = dto.getContent();
        if (dto.getPrice() > 0) this.price = dto.getPrice();
        if (dto.getStatus() != null) this.status = dto.getStatus();

        if (newOriginName != null && newChangeName != null) {
            this.originName = newOriginName;
            this.changeName = newChangeName;
        }
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = (viewCount == null) ? 0 : viewCount;
    }
}