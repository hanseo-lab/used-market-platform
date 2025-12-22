package com.kh.board.dto.response;

import com.kh.board.entity.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private Long id;
    private String email;
    private String name;
    private String phone;
    private String address;
    private String detailAddress;
    // createdAt 등 필요한 필드 추가 가능
    // 보안상 password는 절대 반환하지 않습니다.

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.phone = member.getPhone();
        this.address = member.getAddress();
        this.detailAddress = member.getDetailAddress();
    }
}