package com.kh.board.service;

import com.kh.board.dto.request.MemberUpdateDto;
import com.kh.board.entity.Member;
import com.kh.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member login(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다."));
    }

    @Override
    @Transactional
    public Member signup(Member member) {
        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        return memberRepository.save(member);
    }

    @Override
    @Transactional
    public Member updateMember(Long id, MemberUpdateDto dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다."));

        if (dto.getAddress() != null) {
            member.setAddress(dto.getAddress());
        }
        // 비밀번호가 입력되었을 때만 변경
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            member.setPassword(dto.getPassword());
        }
        return member;
    }
}