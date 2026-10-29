package com.aloha.shop.repository.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aloha.shop.domain.users.User;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 사용자 이름으로 회원을 조회합니다.
     * SELECT *
     * FROM users
     * WHERE username = :username
     * @param username
     * @return
     */
    Optional<User> findByUsername(String username);

    /**
     * 사용자 이름으로 회원 존재 여부를 확인합니다.
     * SELECT COUNT(*)
     * FROM users
     * WHERE username = :username
     * @param username
     * @return
     */
    boolean existsByUsername(String username);
}
