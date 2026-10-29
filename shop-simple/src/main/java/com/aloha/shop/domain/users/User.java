package com.aloha.shop.domain.users;

import java.util.ArrayList;
import java.util.List;

import com.aloha.shop.domain.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 회원 엔터티
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;                    // 회원번호(PK)

    @Column(nullable = false, unique = true, length = 50)
    private String username;            // 로그인 아이디

    @Column(nullable = false)
    private String password;            // 비밀번호(암호화)

    @Column(nullable = false, length = 50)
    private String name;                // 이름

    @Column(length = 100)
    private String email;               // 이메일

    @Column(length = 30)
    private String phone;               // 연락처

    @Builder.Default
    @Column(nullable = false)
    private boolean enabled = true;     // 계정 활성화 여부

    // 회원 : 권한 = 1 : N
    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAuth> authList = new ArrayList<>();

    // 회원 : 배송지 = 1 : N
    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    /** 권한 추가 편의 메서드 */
    public void addAuth(String auth) {
        UserAuth role = UserAuth.builder()
                                .auth(auth)
                                .user(this)
                                .build();
        this.authList.add(role);
    }
}
