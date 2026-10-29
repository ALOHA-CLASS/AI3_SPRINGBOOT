package com.aloha.jpashop.repository;

import com.aloha.jpashop.entity.OrderItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT oi FROM OrderItem oi JOIN FETCH oi.product WHERE oi.orders.no = :orderId")
    List<OrderItem> findByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT COALESCE(SUM(oi.orderPrice * oi.quantity), 0) FROM OrderItem oi WHERE oi.orders.no = :orderId")
    int getOrderTotal(@Param("orderId") Long orderId);
}
