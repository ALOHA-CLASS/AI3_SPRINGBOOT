package com.aloha.security_querydsl.repository;

import java.util.Optional;

import com.aloha.security_querydsl.domain.Users;

/**
 * QueryDSL 를 이용한 회원 조회 기능
 */
public interface UserRepositoryCustom {

  // 회원 + 권한 목록을 fetch join 으로 한번에 조회 (인증 시 지연로딩 예외 방지)
  Optional<Users> findByUsernameWithAuth(String username);

}
