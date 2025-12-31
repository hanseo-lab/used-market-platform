package com.kh.board.entity;

import com.kh.board.dto.request.MemberUpdateDto;
import com.kh.board.global.common.CommonEnums;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String phone;

    private String address;

    private String detailAddress;

    @Enumerated(EnumType.STRING)
    private CommonEnums.Role role;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // [수정] 회원 정보 수정 메서드 (Setter 대신 이 메서드를 사용)
    public void updateMember(MemberUpdateDto dto) {
        // 이름 수정 로직 추가
        if (dto.getName() != null) {
            this.name = dto.getName();
        }

        if (dto.getAddress() != null) {
            this.address = dto.getAddress();
        }

        if (dto.getDetailAddress() != null){
            this.detailAddress = dto.getDetailAddress();
        }

        if (dto.getPhone() != null) {
            this.phone = dto.getPhone();
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            this.password = dto.getPassword();
        }
    }
}