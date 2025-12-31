package com.kh.board.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LoginRequest {
        @JsonProperty("email")
        private String email;

        @JsonProperty("password")
        private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LoginResponse {
        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("grant_type")
        private String grantType;

        @JsonProperty("email")
        private String email;

        @JsonProperty("name")
        private String name;
    }
}