package com.kh.board.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    private String imageUrl;

    private String category;

    @ColumnDefault("0")
    private int viewCount;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(columnDefinition = "VARCHAR(20) DEFAULT 'FOR_SALE'")
    private String status; // FOR_SALE, RESERVED, SOLD_OUT
}