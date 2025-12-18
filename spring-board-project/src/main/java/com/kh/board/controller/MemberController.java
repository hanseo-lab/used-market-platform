package com.kh.board.controller;

import com.kh.board.dto.request.MemberUpdateDto;
import com.kh.board.dto.request.MemberWithdrawDto; // DTO import
import com.kh.board.entity.Member;
import com.kh.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Member> signup(@RequestBody Member member) {
        return ResponseEntity.ok(memberService.signup(member));
    }

    @PostMapping("/login")
    public ResponseEntity<Member> login(@RequestBody Member member) {
        return ResponseEntity.ok(memberService.login(member.getEmail(), member.getPassword()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody MemberUpdateDto dto) {
        return ResponseEntity.ok(memberService.updateMember(id, dto));
    }

    // [수정] 비밀번호를 함께 받음
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id, @RequestBody MemberWithdrawDto dto) {
        memberService.deleteMember(id, dto.getPassword());
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }
}