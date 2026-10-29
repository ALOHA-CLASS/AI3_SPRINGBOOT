package com.aloha.jpashop.service;

import com.aloha.jpashop.entity.OrderStatus;
import com.aloha.jpashop.entity.Orders;
import com.aloha.jpashop.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrdersRepository ordersRepository;

    @Override
    public List<Orders> findByUserNo(Long userNo) {
        return ordersRepository.findByUserId(userNo);
    }

    @Override
    public Orders findDetail(Long id) {
        return ordersRepository.findDetail(id).orElse(null);
    }

    @Override
    public List<Object[]> countByStatus() {
        return ordersRepository.countByStatus();
    }

    @Override
    public List<Orders> findByStatus(OrderStatus status) {
        return ordersRepository.findByStatus(status);
    }

    @Override
    public Orders findByNo(Long no) {
        return ordersRepository.findById(no).orElse(null);
    }

    @Override
    public long count() {
        return ordersRepository.count();
    }
}
