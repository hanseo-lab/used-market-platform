package com.kh.board.controller;

import com.kh.board.dto.request.MemberUpdateDto;
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

    @PostMapping("/login")
    public ResponseEntity<Member> login(@RequestBody Member member) {
        return ResponseEntity.ok(memberService.login(member.getEmail(), member.getPassword()));
    }

    @PostMapping("/signup")
    public ResponseEntity<Member> signup(@RequestBody Member member) {
        return ResponseEntity.ok(memberService.signup(member));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody MemberUpdateDto dto) {
        return ResponseEntity.ok(memberService.updateMember(id, dto));
    }
}