package com.kh.board.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductUpdateDto {
    private String title;
    private String content;
    private int price;
    private String status; // 판매상태 변경 (FOR_SALE, SOLD_OUT)
}