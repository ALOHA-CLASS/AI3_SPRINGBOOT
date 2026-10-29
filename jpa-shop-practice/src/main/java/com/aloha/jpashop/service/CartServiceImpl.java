package com.aloha.jpashop.service;

import com.aloha.jpashop.entity.Cart;
import com.aloha.jpashop.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    public Cart findByNo(Long no) {
        return cartRepository.findById(no).orElse(null);
    }

    @Override
    public Cart findByUserId(Long userId) {
        return cartRepository.findByUserId(userId).orElse(null);
    }

    @Override
    public long count() {
        return cartRepository.count();
    }
}
