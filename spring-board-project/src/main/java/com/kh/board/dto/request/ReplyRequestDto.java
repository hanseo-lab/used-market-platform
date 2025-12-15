package com.kh.board.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyRequestDto {
    private Long memberId;
    private String content;
}