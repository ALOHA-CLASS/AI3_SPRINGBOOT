package com.aloha.shop.repository.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.aloha.shop.domain.shop.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long>, OrderRepositoryCustom {

    Optional<Orders> findByOrderNo(String orderNo);
}
