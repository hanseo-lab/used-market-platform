package com.kh.board.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kh.board.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MemberDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignupRequest {
        @JsonProperty("email")
        private String email;

        @JsonProperty("password")
        private String password;

        @JsonProperty("name")
        private String name;

        @JsonProperty("phone")
        private String phone;

        @JsonProperty("address")
        private String address;

        @JsonProperty("detail_address")
        private String detailAddress;

        public Member toEntity(PasswordEncoder passwordEncoder) {
            return Member.builder()
                    .email(email)
                    .password(passwordEncoder.encode(password)) // 암호화 필수
                    .name(name)
                    .phone(phone)
                    .address(address)
                    .detailAddress(detailAddress)
                    .build();
        }
    }
}