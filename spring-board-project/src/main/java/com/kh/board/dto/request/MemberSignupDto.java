package com.kh.board.dto.request;

import com.kh.board.entity.Member;
import com.kh.board.global.common.CommonEnums;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
public class MemberSignupDto {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;
    private String detailAddress;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(this.email)
                .password(passwordEncoder.encode(this.password)) // 암호화 필수
                .name(this.name)
                .phone(this.phone)
                .address(this.address)
                .detailAddress(this.detailAddress)
                .role(CommonEnums.Role.USER)
                .build();
    }
}