package com.kh.board.service;

import com.kh.board.dto.request.LoginRequestDto;
import com.kh.board.dto.request.MemberSignupDto;
import com.kh.board.dto.request.MemberUpdateDto;
import com.kh.board.dto.response.MemberResponseDto;
import com.kh.board.entity.Member;
import com.kh.board.global.security.JwtTokenProvider;
import com.kh.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @Override
    @Transactional
    public Long signup(MemberSignupDto requestDto) {
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다: " + requestDto.getEmail());
        }
        Member member = requestDto.toEntity(passwordEncoder);
        memberRepository.save(member);
        return member.getId();
    }

    // 로그인
    @Override
    public String login(LoginRequestDto requestDto) {
        // 1. 이메일로 회원 조회
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 인증 토큰 생성용 객체 (Principal: email, 권한: ROLE_ 접두사 추가)
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), null,
                        java.util.Collections.singletonList(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + member.getRole().name())));

        // 4. JWT 토큰 생성
        return jwtTokenProvider.generateToken(authenticationToken);
    }

    // 회원 정보 수정
    @Override
    @Transactional
    public Member updateMember(Long id, MemberUpdateDto dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        member.updateMember(dto);

        return member;
    }

    // 회원 단건 조회
    @Override
    public MemberResponseDto getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다. ID: " + memberId));
        return MemberResponseDto.from(member);
    }

    // 회원 탈퇴
    @Override
    @Transactional
    public void deleteMember(Long memberId, String password) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        memberRepository.delete(member);
    }
}