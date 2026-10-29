package com.aloha.shop.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.aloha.shop.domain.users.User;

import lombok.Getter;

/**
 * Spring Security 인증 사용자 (User 엔터티 래핑)
 */
@Getter
public class CustomUser implements UserDetails {

    private final User user;

    public CustomUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthList().stream()
                   .map(r -> new SimpleGrantedAuthority(r.getAuth()))
                   .map(a -> (GrantedAuthority) a)
                   .toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    /** 화면 표시용 이름 */
    public String getName() {
        return user.getName();
    }

    /** 회원번호 */
    public Long getNo() {
        return user.getNo();
    }

    /** 정적 헬퍼 - 기본 권한 목록 */
    public static List<String> defaultAuthList() {
        return List.of("ROLE_USER"); // 기본 권한 목록
    }
}
