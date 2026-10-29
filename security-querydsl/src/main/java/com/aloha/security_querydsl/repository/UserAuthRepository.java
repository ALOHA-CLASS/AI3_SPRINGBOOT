package com.aloha.security_querydsl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aloha.security_querydsl.domain.UserAuth;

// JPA 레포지토리 (회원 권한)
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
}
