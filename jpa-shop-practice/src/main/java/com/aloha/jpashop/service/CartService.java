package com.aloha.jpashop.service;

import com.aloha.jpashop.entity.Cart;

public interface CartService {

    Cart findByNo(Long no);

    Cart findByUserId(Long userId);

    long count();
}
