package com.aloha.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.aloha.shop.security.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

/**
 * Spring Security 설정 - 사용자단 쇼핑몰
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 인가 설정
        http.authorizeHttpRequests(auth -> auth
                // 정적 리소스 / 공개 페이지
                .requestMatchers("/", "/css/**", "/js/**", "/img/**", "/images/**").permitAll()
                .requestMatchers("/users/join", "/users/login").permitAll()
                .requestMatchers("/products", "/products/**").permitAll()
                // 로그인 필요 영역
                .requestMatchers("/cart/**", "/orders/**", "/mypage/**").authenticated()
                .anyRequest().permitAll()
        );

        // 폼 로그인
        http.formLogin(login -> login
                .loginPage("/users/login")
                .loginProcessingUrl("/users/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureUrl("/users/login?error=true")
                .permitAll()
        );

        // 로그아웃
        http.logout(logout -> logout
                .logoutUrl("/users/logout")
                .logoutSuccessUrl("/?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
        );

        // 사용자 정의 인증
        http.userDetailsService(customUserDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
