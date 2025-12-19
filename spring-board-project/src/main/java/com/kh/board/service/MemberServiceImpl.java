package com.kh.board.service;

import com.kh.board.dto.request.MemberUpdateDto;
import com.kh.board.entity.Member;
import com.kh.board.entity.Product;
import com.kh.board.exception.ResourceNotFoundException; // [추가]
import com.kh.board.repository.MemberRepository;
import com.kh.board.repository.ProductRepository;
import com.kh.board.repository.CommentRepository;
import com.kh.board.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CommentRepository replyRepository;
    private final WishlistRepository wishlistRepository;

    @Override
    public Member login(String email, String password) {
        // 로그인 실패는 리소스 없음이 아니라 인증 실패이므로 예외 메시지로 처리 (400 Bad Request)
        return memberRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다."));
    }

    @Override
    @Transactional
    public Member signup(Member member) {
        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다."); // 400 (Conflict 대신 400 사용)
        }
        return memberRepository.save(member);
    }

    @Override
    @Transactional
    public Member updateMember(Long id, MemberUpdateDto dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("회원 정보가 없습니다.")); // [수정] 404
        member.updateMember(dto);
        return member;
    }

    @Override
    @Transactional
    public void deleteMember(Long id, String password) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 회원입니다.")); // [수정] 404

        if (!member.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다."); // 400
        }

        wishlistRepository.deleteByMemberId(id);
        replyRepository.deleteByMemberId(id);

        List<Product> myProducts = productRepository.findBySeller(member.getName());
        for (Product product : myProducts) {
            productRepository.deleteById(product.getId());
        }

        memberRepository.deleteById(id);
    }
}