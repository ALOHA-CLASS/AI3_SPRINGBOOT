package com.aloha.security_querydsl.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.aloha.security_querydsl.domain.QBoard;
import com.aloha.security_querydsl.domain.QUsers;
import com.aloha.security_querydsl.domain.Board;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

/**
 * BoardRepository 의 커스텀 구현클래스
 * 이름 규칙(Repository 인터페이스명 + Impl) 형태로 쓰면
 * Spring Data JPA가 자동으로 인식한다.
 */
@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

  private final JPAQueryFactory queryFactory; // QuerydslConfig 에서 등록한 빈 주입

  private static final QBoard board = QBoard.board; // Board 의 Q타입 인스턴스
  private static final QUsers user = QUsers.users;   // Users 의 Q타입 인스턴스

  @Override
  public List<Board> findAllWithUser() {
    return queryFactory
        .selectFrom(board)
        .join(board.user, user).fetchJoin() // 작성자 정보 즉시 로딩 (N+1 방지)
        .orderBy(board.no.desc())
        .fetch();
  }

  @Override
  public Optional<Board> findByIdWithUser(String id) {
    Board result = queryFactory
        .selectFrom(board)
        .join(board.user, user).fetchJoin()
        .where(board.id.eq(id))
        .fetchOne();
    return Optional.ofNullable(result);
  }

  @Override
  public boolean isOwner(String id, Long userNo) {
    Long no = queryFactory
        .select(board.no)
        .from(board)
        .where(
            board.id.eq(id),
            board.user.no.eq(userNo) // 작성자 회원번호와 비교
        )
        .fetchFirst();
    return no != null;
  }

}
