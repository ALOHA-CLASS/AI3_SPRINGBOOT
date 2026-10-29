package com.aloha.shop.service.shop;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aloha.shop.domain.shop.CartItem;

public interface CartService {

    void addToCart(Long userNo, Long productNo, int quantity);

    Page<CartItem> list(Long userNo, Pageable pageable);

    List<CartItem> listAll(Long userNo);

    CartItem updateQuantity(Long userNo, Long cartItemNo, int quantity);

    void remove(Long userNo, Long cartItemNo);

    long count(Long userNo);

    void clear(Long userNo);

    int totalPrice(Long userNo);
}
