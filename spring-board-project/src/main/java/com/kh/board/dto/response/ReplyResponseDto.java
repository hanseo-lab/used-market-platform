package com.kh.board.dto.response;

import com.kh.board.entity.Reply;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
public class ReplyResponseDto {
    private Long id;
    private String content;
    private String writerName;
    private LocalDateTime createdAt;

    public ReplyResponseDto(Reply reply) {
        this.id = reply.getId();
        this.content = reply.getContent();
        this.writerName = reply.getMember().getName();
        this.createdAt = reply.getCreatedAt();
    }
}