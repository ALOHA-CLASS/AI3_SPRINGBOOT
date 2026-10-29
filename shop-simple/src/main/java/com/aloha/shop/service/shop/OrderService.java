package com.aloha.shop.service.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aloha.shop.domain.shop.Orders;
import com.aloha.shop.dto.shop.OrderForm;

public interface OrderService {

    // 장바구니 전체를 주문으로 전환
    Orders orderFromCart(Long userNo, OrderForm form);

    // 장바구니를 거치지 않고 상품 1건을 바로 주문
    Orders orderDirect(Long userNo, Long productNo, int quantity, OrderForm form);

    Page<Orders> list(Long userNo, Pageable pageable);

    Orders get(Long userNo, String orderNo);

    void cancel(Long userNo, String orderNo);
}
