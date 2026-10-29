package com.aloha.shop.repository.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aloha.shop.domain.shop.Orders;

/**
 * QueryDSL 주문 조회 커스텀 인터페이스
 */
public interface OrderRepositoryCustom {

    /**
     * 사용자의 주문 목록을 페이징 처리하여 조회합니다.
     * SELECT *
     * FROM orders
     * WHERE user_no = :userNo
     * ORDER BY order_date DESC
     * @param userNo
     * @param pageable
     * @return
     */
    Page<Orders> findByUser(Long userNo, Pageable pageable);
}
