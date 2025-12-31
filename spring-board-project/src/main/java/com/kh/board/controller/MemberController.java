package com.kh.board.controller;

import com.kh.board.dto.request.LoginRequestDto;
import com.kh.board.dto.request.MemberSignupDto;
import com.kh.board.dto.request.MemberUpdateDto;
import com.kh.board.dto.response.MemberResponseDto;
import com.kh.board.entity.Member;
import com.kh.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 1. 회원가입 (파라미터 타입 MemberSignupDto로 변경)
    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@RequestBody MemberSignupDto request) {
        Long memberId = memberService.signup(request);
        return ResponseEntity.ok(memberId);
    }

    // 2. 로그인 (토큰 반환하도록 수정)
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDto requestDto) {
        String token = memberService.login(requestDto);

        Map<String, String> response = new HashMap<>();
        response.put("accessToken", token);

        return ResponseEntity.ok(response);
    }

    // 3. 회원 정보 수정
    @PatchMapping("/{id}")
    public ResponseEntity<MemberResponseDto> updateMember(@PathVariable Long id, @RequestBody MemberUpdateDto dto) {
        Member updatedMember = memberService.updateMember(id, dto);
        return ResponseEntity.ok(MemberResponseDto.from(updatedMember));
    }

    // 4. 회원 탈퇴
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id, @RequestHeader("password") String password) {
        memberService.deleteMember(id, password);
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }
}