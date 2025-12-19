package com.kh.board.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberSignupDto {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;
}