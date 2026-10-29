package com.aloha.jpashop.repository;

import com.aloha.jpashop.entity.Cart;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c WHERE c.user.no = :userId")
    Optional<Cart> findByUserId(@Param("userId") Long userId);
}
