package com.kh.board.service;

import com.kh.board.dto.request.MemberUpdateDto;
import com.kh.board.entity.Member;
import com.kh.board.entity.Product;
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
        member.updateMember(dto);
        return member;
    }

    @Override
    @Transactional
    public void deleteMember(Long id, String password) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // ★ 비밀번호 확인
        if (!member.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 1. 찜 목록 삭제
        wishlistRepository.deleteByMemberId(id);

        // 2. 댓글 삭제
        replyRepository.deleteByMemberId(id);

        // 3. 상품 삭제
        List<Product> myProducts = productRepository.findBySeller(member.getName());
        for (Product product : myProducts) {
            productRepository.deleteById(product.getId());
        }

        // 4. 회원 삭제
        memberRepository.deleteById(id);
    }
}