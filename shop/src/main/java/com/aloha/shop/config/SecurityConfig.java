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
 * Spring Security 설정
 */
@Configuration                // 스프링 빈 설정 클래스로 지정
@EnableWebSecurity            // 스프리 시큐리티 설정 클래스로 지정
@RequiredArgsConstructor      // 필수 매개변수 생성자 자동 생성
public class SecurityConfig {

  private final CustomUserDetailsService customUserDetailsService;

  // ⚡ 스프링 시큐리티 설정 방법
  // - SecurityFilterChain 을 빈 등록해서 인가설정, 폼로그인 설정 등을 한다.
  @Bean 
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // 인가 설정
    http.authorizeHttpRequests(auth -> auth
        // 공개 페이지, 정적 자원
        .requestMatchers("/", "/css/**", "/js/**", "/img/**", "/images/**").permitAll()
        .requestMatchers("/users/join", "/users/login").permitAll()
        .requestMatchers("/products", "/products/**").permitAll()
        // 로그인 필요 영역
        .requestMatchers("/cart/**", "/orders/**", "/mypage/**", "/users/mypage/**").authenticated()
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

    // 사용자 정의 인증
    http.userDetailsService(customUserDetailsService);

    // 자동 로그인 (remember-me)
    http.rememberMe(remember -> remember
          .key("aloha-shop-remember-me")               // 토큰 생성용 키
          .userDetailsService(customUserDetailsService) // 사용자 조회 서비스
          .rememberMeParameter("remember-me")            // 로그인 폼 체크박스 name
          .tokenValiditySeconds(60 * 60 * 24 * 14)       // 토큰 유효기간 14일
    );

    // 로그아웃
    http.logout(logout -> logout
                      .logoutUrl("/users/logout")
                      .logoutSuccessUrl("/?logout=true")
                      .invalidateHttpSession(true)
                      .deleteCookies("JSESSIONID", "remember-me")
    );


    return http.build();
  }


  // 비밀번호 암호화 객체 빈 등록
  @Bean 
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
}
