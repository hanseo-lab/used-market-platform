package com.kh.board.service;

import com.kh.board.dto.AuthDto;
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
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public AuthDto.LoginResponse login(AuthDto.LoginRequest request) {
        // 1. 이메일로 회원 조회
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 2. 비밀번호 검증 (request.password vs member.password)
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 인증 토큰 생성용 객체 (Principal: email)
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), null);

        // 4. JWT 토큰 생성
        String token = jwtTokenProvider.generateToken(authenticationToken);

        return AuthDto.LoginResponse.builder()
                .accessToken(token)
                .grantType("Bearer")
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
}