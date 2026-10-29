package com.aloha.security_querydsl.service;

import java.util.List;

import com.aloha.security_querydsl.domain.Board;

public interface BoardService {
  // 목록
  public List<Board> list() throws Exception;
  // 조회 - id
  public Board selectById(String id) throws Exception;
  // 등록
  public boolean insert(Board board, Long userNo) throws Exception;
  // 수정 - id
  public boolean updateById(Board board) throws Exception;
  // 삭제 - id
  public boolean deleteById(String id) throws Exception;

  // ⭐ 소유자 확인
  public boolean isOwner(String id, Long userNo) throws Exception;
}
