package com.aloha.jpashop.repository;

import com.aloha.jpashop.entity.Users;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query("SELECT u FROM Users u ORDER BY u.no DESC")
    List<Users> findAllByJpql();

    @Query("SELECT u FROM Users u WHERE u.username LIKE CONCAT('%', :keyword, '%')")
    List<Users> search(@Param("keyword") String keyword);
}
