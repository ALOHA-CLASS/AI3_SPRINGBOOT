package com.aloha.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aloha.security.dto.Board;

@Mapper
public interface BoardMapper {
  // 목록
  public List<Board> list() throws Exception;
  // 조회
  public Board select(Long no) throws Exception;
  // 조회 - id
  public Board selectById(String id) throws Exception;
  // 등록
  public int insert(Board board) throws Exception;
  // 수정
  public int update(Board board) throws Exception;
  // 수정 - id
  public int updateById(Board board) throws Exception;
  // 삭제
  public int delete(Long no) throws Exception;
  // 삭제 - id
  public int deleteById(String id) throws Exception;

}
