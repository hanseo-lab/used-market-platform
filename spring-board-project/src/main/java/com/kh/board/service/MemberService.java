package com.kh.board.service;

import com.kh.board.dto.request.LoginRequestDto;
import com.kh.board.dto.request.MemberSignupDto;
import com.kh.board.dto.request.MemberUpdateDto;
import com.kh.board.dto.response.MemberResponseDto;
import com.kh.board.entity.Member;

public interface MemberService {

    // 회원가입
    Long signup(MemberSignupDto requestDto);

    // 로그인 (토큰 문자열 반환)
    String login(LoginRequestDto requestDto);

    // 회원 정보 수정 (추가됨)
    Member updateMember(Long id, MemberUpdateDto requestDto);

    // 회원 조회
    MemberResponseDto getMember(Long memberId);

    // 회원 탈퇴 (비밀번호 확인 추가)
    void deleteMember(Long memberId, String password);
}