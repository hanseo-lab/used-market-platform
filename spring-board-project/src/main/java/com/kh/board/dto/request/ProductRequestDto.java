package com.kh.board.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class ProductRequestDto {
    private String title;
    private String content;
    private int price;
    private String seller;
    private String category;
    private MultipartFile imageFile;
}