package com.aloha.jpashop.service;

import com.aloha.jpashop.entity.OrderItem;
import com.aloha.jpashop.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItem> findByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    @Override
    public OrderItem findById(Long id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    @Override
    public int getOrderTotal(Long orderId) {
        return orderItemRepository.getOrderTotal(orderId);
    }

    @Override
    public long count() {
        return orderItemRepository.count();
    }
}
