package com.aloha.jpashop.service;

import com.aloha.jpashop.entity.OrderStatus;
import com.aloha.jpashop.entity.Orders;

import java.util.List;

public interface OrderService {

    List<Orders> findByUserNo(Long userNo);

    Orders findDetail(Long id);

    List<Object[]> countByStatus();

    List<Orders> findByStatus(OrderStatus status);

    Orders findByNo(Long no);

    long count();
}
