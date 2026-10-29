package com.aloha.security.service;

import java.util.List;

import com.aloha.security.dto.Board;

public interface BoardService {
  // 목록
  public List<Board> list() throws Exception;
  // 조회
  public Board select(Long no) throws Exception;
  // 조회 - id
  public Board selectById(String id) throws Exception;
  // 등록
  public boolean insert(Board board) throws Exception;
  // 수정
  public boolean update(Board board) throws Exception;
  // 수정 - id
  public boolean updateById(Board board) throws Exception;
  // 삭제
  public boolean delete(Long no) throws Exception;
  // 삭제 - id
  public boolean deleteById(String id) throws Exception;

  // ⭐ 소유자 확인
  public boolean isOwner(String id, Long userNo) throws Exception;
}
