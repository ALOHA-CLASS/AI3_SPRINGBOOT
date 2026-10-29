package com.aloha.shop.repository.shop;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aloha.shop.domain.shop.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // 장바구니 목록 - 페이징
    Page<CartItem> findByUserNo(Long userNo, Pageable pageable);

    // 전체 목록(주문 시 사용)
    java.util.List<CartItem> findByUserNo(Long userNo);

    // 동일 상품 중복 체크
    Optional<CartItem> findByUserNoAndProductNo(Long userNo, Long productNo);

    long countByUserNo(Long userNo);

    void deleteByUserNo(Long userNo);
}
