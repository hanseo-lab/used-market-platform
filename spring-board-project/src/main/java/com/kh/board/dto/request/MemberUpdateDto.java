package com.kh.board.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateDto {
    private String name;
    private String address;
    private String password;
    private String phone;
    private String detailAddress;
}