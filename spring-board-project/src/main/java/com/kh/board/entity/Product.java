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

    private int price;

    private String seller;

    // [변경] 기존 imageUrl 제거하고 원본/변경 파일명으로 분리
    private String originName;
    private String changeName;

    private String category;

    @ColumnDefault("0")
    private int viewCount;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // [변경] Enum 타입 적용
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @ColumnDefault("'FOR_SALE'")
    @Builder.Default
    private ProductStatus status = ProductStatus.FOR_SALE;

    // [수정 메서드] DTO와 파일명 정보를 받아 필드 업데이트
    public void updateProduct(ProductUpdateDto dto, String newOriginName, String newChangeName) {
        if (dto.getTitle() != null) this.title = dto.getTitle();
        if (dto.getContent() != null) this.content = dto.getContent();
        if (dto.getPrice() > 0) this.price = dto.getPrice();
        if (dto.getStatus() != null) this.status = dto.getStatus();

        // 파일이 새로 업로드된 경우에만 덮어쓰기
        if (newOriginName != null && newChangeName != null) {
            this.originName = newOriginName;
            this.changeName = newChangeName;
        }
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}