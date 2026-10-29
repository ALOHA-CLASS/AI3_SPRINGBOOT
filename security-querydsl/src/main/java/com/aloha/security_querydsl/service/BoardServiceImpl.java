package com.aloha.security_querydsl.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloha.security_querydsl.domain.Board;
import com.aloha.security_querydsl.domain.Users;
import com.aloha.security_querydsl.repository.BoardRepository;
import com.aloha.security_querydsl.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("BoardService")    // 빈이름 : BoardService 
                            // ⭐ @PreAuthorized(" @BoardService.isOwner() ") 형태로 사용할 수 있음.
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

  private final BoardRepository boardRepository;
  private final UserRepository userRepository;

  @Override
  public List<Board> list() throws Exception {
    return boardRepository.findAllWithUser();
  }

  @Override
  public Board selectById(String id) throws Exception {
    return boardRepository.findByIdWithUser(id).orElse(null);
  }

  @Override
  @Transactional
  public boolean insert(Board board, Long userNo) throws Exception {
    // 작성자(회원) 프록시 참조를 세팅 (조회용 SELECT 없이 FK 참조만 설정)
    Users userRef = userRepository.getReferenceById(userNo);
    board.setUser(userRef);

    Board saved = boardRepository.save(board);
    return saved.getNo() != null;
  }

  @Override
  @Transactional
  public boolean updateById(Board board) throws Exception {
    Board target = boardRepository.findByIdWithUser(board.getId())
                                .orElseThrow(() -> new Exception("게시글을 찾을 수 없습니다."));

    if( board.getTitle() != null && !board.getTitle().isEmpty() ) {
      target.setTitle(board.getTitle());
    }
    if( board.getContent() != null && !board.getContent().isEmpty() ) {
      target.setContent(board.getContent());
    }
    boardRepository.save(target);
    return true;
  }

  @Override
  @Transactional
  public boolean deleteById(String id) throws Exception {
    Board target = boardRepository.findByIdWithUser(id)
                                .orElseThrow(() -> new Exception("게시글을 찾을 수 없습니다."));
    boardRepository.delete(target);
    return true;
  }

  /**
   * @param id          : 게시글 id (board-id)
   * @param userNo      : 회원 no   (user-no)
   * 게시글 id 로 작성자 userNo 를 QueryDSL 로 조회하여,
   * 인증된(로그인) 사용자의 no 와 일치하는지 확인
   */
  @Override
  public boolean isOwner(String id, Long userNo) throws Exception {
    log.info("게시글 id : {}", id);
    log.info("로그인한 회원 no : {}", userNo);
    return boardRepository.isOwner(id, userNo);
  }
  
}
