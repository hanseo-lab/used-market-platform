package com.kh.board.entity;

import com.kh.board.dto.request.MemberUpdateDto;
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

    private String address;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void updateMember(MemberUpdateDto dto) {
        if (dto.getAddress() != null) {
            this.address = dto.getAddress();
        }
        // 비밀번호가 입력된 경우에만 변경
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            this.password = dto.getPassword();
        }
    }
}