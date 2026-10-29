package com.aloha.security.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aloha.security.dto.Board;
import com.aloha.security.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("BoardService")    // 빈이름 : BoardService 
                            // ⭐ @PreAuthorized(" @BoardService.isOwner() ") 형태로 사용할 수 있음.
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

  private final BoardMapper boardMapper;

  @Override
  public List<Board> list() throws Exception {
    return boardMapper.list();
  }

  @Override
  public Board select(Long no) throws Exception {
    Board board = boardMapper.select(no);
    return board;
  }

  @Override
  public Board selectById(String id) throws Exception {
    Board board = boardMapper.selectById(id);
    return board;
  }

  @Override
  public boolean insert(Board board) throws Exception {
    int result = boardMapper.insert(board);
    return result > 0;
  }

  @Override
  public boolean update(Board board) throws Exception {
    int result = boardMapper.update(board);
    return result > 0;
  }

  @Override
  public boolean updateById(Board board) throws Exception {
    int result = boardMapper.updateById(board);
    return result > 0;
  }

  @Override
  public boolean delete(Long no) throws Exception {
    int result = boardMapper.delete(no);
    return result > 0;
  }

  @Override
  public boolean deleteById(String id) throws Exception {
    int result = boardMapper.deleteById(id);
    return result > 0;
  }

  /**
   * @param id          : 게시글 id (board-id)
   * @param userNo      : 회원 no   (user-no)
   * 게시글 id 로 작성자 userNo 를 조회하여,
   * 인증된(로그인) 사용자의 no 와 일치하는지 확인
   */
  @Override
  public boolean isOwner(String id, Long userNo) throws Exception {
    log.info("게시글 id : {}", id);
    log.info("로그인한 회원 no : {}", userNo);

    Board board = selectById(id);
    Long boardUserNo = board.getUserNo();
    log.info("작성자 회원 no : {}", boardUserNo);

    // 로그인 회원번호(userNo) == 작성자 회원번호(boardUserNo) 같으면, 작성자 본인
    if( userNo != null && userNo == boardUserNo )  {
      return true;
    }
    return false;
  }
  
}
