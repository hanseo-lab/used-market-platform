package com.kh.board.dto.response;

import com.kh.board.entity.Comment;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
public class CommentResponseDto {
    private Long id;
    private String content;
    private String writerName;
    private Long memberId;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment Comment) {
        this.id = Comment.getId();
        this.content = Comment.getContent();
        this.writerName = Comment.getMember().getName();
        this.memberId = Comment.getMember().getId();
        this.createdAt = Comment.getCreatedAt();
    }
}