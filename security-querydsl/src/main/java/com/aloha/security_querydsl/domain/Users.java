package com.aloha.security_querydsl.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 회원
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "authList")
public class Users extends BaseEntity {

  @Column(nullable = false, unique = true, length = 100)
  private String username;

  @Column(nullable = false, length = 200)
  private String password;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(length = 200)
  private String email;

  @Builder.Default
  @Column(nullable = false)
  private int enabled = 1;

  // 회원 권한 목록 (users:user_auth = 1:N)
  @Builder.Default
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<UserAuth> authList = new ArrayList<>();

  // 회원 권한 추가 (편의 메소드)
  public void addAuth(UserAuth userAuth) {
    userAuth.setUser(this);
    this.authList.add(userAuth);
  }
}
