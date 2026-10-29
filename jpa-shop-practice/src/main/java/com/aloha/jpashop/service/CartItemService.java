package com.aloha.jpashop.service;

import com.aloha.jpashop.entity.CartItem;

import java.util.List;

public interface CartItemService {

    List<CartItem> findByCartId(Long cartId);

    List<CartItem> findByUserId(Long userId);

    CartItem findById(Long id);

    long count();
}
