package com.kh.board.controller;

import com.kh.board.dto.request.LoginRequestDto;
import com.kh.board.dto.request.MemberSignupDto;
import com.kh.board.dto.request.MemberUpdateDto;
import com.kh.board.dto.response.MemberResponseDto;
import com.kh.board.entity.Member;
import com.kh.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 1. 회원가입
     */
    @PostMapping
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberSignupDto requestDto) {
        Member member = Member.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .name(requestDto.getName())
                .phone(requestDto.getPhone())
                .address(requestDto.getAddress())
                .detailAddress(requestDto.getDetailAddress()) // [추가] 상세 주소 저장
                .build();

        Member savedMember = memberService.signup(member);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MemberResponseDto(savedMember));
    }

    /**
     * 2. 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<MemberResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        Member member = memberService.login(requestDto.getEmail(), requestDto.getPassword());
        return ResponseEntity.ok(new MemberResponseDto(member));
    }

    /**
     * 3. 회원 정보 수정
     */
    @PatchMapping("/{id}")
    public ResponseEntity<MemberResponseDto> updateMember(@PathVariable Long id, @RequestBody MemberUpdateDto dto) {
        Member updatedMember = memberService.updateMember(id, dto);
        return ResponseEntity.ok(new MemberResponseDto(updatedMember));
    }

    /**
     * 4. 회원 탈퇴
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id, @RequestHeader("password") String password) {
        // 기존 MemberWithdrawDto는 사용하지 않고 바로 password 문자열을 받습니다.
        memberService.deleteMember(id, password);
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }
}