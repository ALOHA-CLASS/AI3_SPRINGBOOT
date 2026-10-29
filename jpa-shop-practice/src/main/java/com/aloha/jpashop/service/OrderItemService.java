package com.aloha.jpashop.service;

import com.aloha.jpashop.entity.OrderItem;

import java.util.List;

public interface OrderItemService {

    List<OrderItem> findByOrderId(Long orderId);

    OrderItem findById(Long id);

    int getOrderTotal(Long orderId);

    long count();
}
