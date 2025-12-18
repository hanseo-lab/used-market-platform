package com.kh.board.service;

import com.kh.board.dto.request.MemberUpdateDto;
import com.kh.board.entity.Member;

public interface MemberService {
    Member login(String email, String password);
    Member signup(Member member);
    Member updateMember(Long id, MemberUpdateDto dto);
    void deleteMember(Long id, String password);
}