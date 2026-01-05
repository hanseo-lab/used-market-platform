package com.kh.board.controller;

import com.kh.board.dto.request.LoginRequestDto;
import com.kh.board.dto.request.MemberSignupDto;
import com.kh.board.dto.request.MemberUpdateDto;
import com.kh.board.dto.response.MemberResponseDto;
import com.kh.board.entity.Member;
import com.kh.board.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    // 1. 회원가입 (파라미터 타입 MemberSignupDto로 변경)
    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@RequestBody MemberSignupDto request) {
        Long memberId = memberService.signup(request);
        return ResponseEntity.ok(memberId);
    }

    // 2. 로그인 (토큰과 사용자 정보 반환)
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequestDto requestDto) {
        String token = memberService.login(requestDto);

        // 로그인한 사용자 정보 조회
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        MemberResponseDto memberDto = MemberResponseDto.from(member);

        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", token);
        response.put("id", memberDto.getId());
        response.put("email", memberDto.getEmail());
        response.put("name", memberDto.getName());
        response.put("phone", memberDto.getPhone());
        response.put("address", memberDto.getAddress());

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