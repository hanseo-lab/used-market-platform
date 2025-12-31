package com.kh.board.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // null인 필드는 JSON 응답에서 제외
public class ErrorResponse {
    private final String message;
    private final boolean success;
    private final Map<String, String> errors; // 유효성 검사 실패 시 상세 필드 에러

    @Builder.Default
    private final LocalDateTime timestamp = LocalDateTime.now();

    // 간단한 에러 메시지 반환
    public static ErrorResponse of(String message) {
        return ErrorResponse.builder()
                .success(false)
                .message(message)
                .build();
    }

    // 상세 에러(유효성 검증 실패 등) 포함 반환
    public static ErrorResponse of(String message, Map<String, String> errors) {
        return ErrorResponse.builder()
                .success(false)
                .message(message)
                .errors(errors)
                .build();
    }
}