package com.kh.board.dto.request;

import com.kh.board.enums.ProductStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class ProductUpdateDto {
    private String title;
    private String content;
    private int price;
    private ProductStatus status; // Enum으로 변경
    private MultipartFile imageFile; // 파일 업로드용 필드 추가
}