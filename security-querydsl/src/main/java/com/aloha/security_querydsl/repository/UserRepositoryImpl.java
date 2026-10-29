package com.aloha.security_querydsl.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.aloha.security_querydsl.domain.QUserAuth;
import com.aloha.security_querydsl.domain.QUsers;
import com.aloha.security_querydsl.domain.Users;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

/**
 * UserRepository 의 커스텀 구현클래스
 * 이름 규칙(Repository 인터페이스명 + Impl) 형태로 쓰면
 * Spring Data JPA가 자동으로 인식한다.
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

  private final JPAQueryFactory queryFactory; // QuerydslConfig 에서 등록한 빈 주입

  private static final QUsers user = QUsers.users;             // Users 의 Q타입 인스턴스
  private static final QUserAuth userAuth = QUserAuth.userAuth; // UserAuth 의 Q타입 인스턴스

  @Override
  public Optional<Users> findByUsernameWithAuth(String username) {
    Users result = queryFactory
        .selectFrom(user)
        .distinct()
        .leftJoin(user.authList, userAuth).fetchJoin() // 권한 목록을 fetch join 으로 즉시 로딩
        .where(user.username.eq(username))
        .fetchOne();
    return Optional.ofNullable(result);
  }

}
