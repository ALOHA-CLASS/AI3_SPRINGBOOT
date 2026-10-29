package com.aloha.security_querydsl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aloha.security_querydsl.domain.Board;

// JPA 레포지토리
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

  // id(UUID) 로 조회 (연관 fetch 없이)
  Board findById(String id);

}
