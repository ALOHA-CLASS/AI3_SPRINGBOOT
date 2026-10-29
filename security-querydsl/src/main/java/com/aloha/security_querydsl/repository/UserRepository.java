package com.aloha.security_querydsl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aloha.security_querydsl.domain.Users;

// JPA 레포지토리
public interface UserRepository extends JpaRepository<Users, Long>, UserRepositoryCustom {

  // 아이디(username) 중복 확인
  boolean existsByUsername(String username);

}
