package com.aloha.shop.service.shop;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloha.shop.domain.shop.CartItem;
import com.aloha.shop.domain.shop.Product;
import com.aloha.shop.domain.users.User;
import com.aloha.shop.repository.shop.CartItemRepository;
import com.aloha.shop.repository.shop.ProductRepository;
import com.aloha.shop.repository.users.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void addToCart(Long userNo, Long productNo, int quantity) {
        if (quantity < 1) quantity = 1;

        // 이미 담긴 상품이면 수량 증가
        CartItem existing = cartItemRepository.findByUserNoAndProductNo(userNo, productNo).orElse(null);
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            cartItemRepository.save(existing);
            return;
        }

        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        Product product = productRepository.findById(productNo)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        CartItem item = CartItem.builder()
                .user(user)
                .product(product)
                .quantity(quantity)
                .build();
        cartItemRepository.save(item);
    }

    @Override
    public Page<CartItem> list(Long userNo, Pageable pageable) {
        return cartItemRepository.findByUserNo(userNo, pageable);
    }

    @Override
    public List<CartItem> listAll(Long userNo) {
        return cartItemRepository.findByUserNo(userNo);
    }

    @Override
    @Transactional
    public CartItem updateQuantity(Long userNo, Long cartItemNo, int quantity) {
        if (quantity < 1) quantity = 1;
        CartItem item = getOwned(userNo, cartItemNo);
        item.setQuantity(quantity);
        return cartItemRepository.save(item);
    }

    @Override
    @Transactional
    public void remove(Long userNo, Long cartItemNo) {
        CartItem item = getOwned(userNo, cartItemNo);
        cartItemRepository.delete(item);
    }

    @Override
    public long count(Long userNo) {
        return cartItemRepository.countByUserNo(userNo);
    }

    @Override
    @Transactional
    public void clear(Long userNo) {
        cartItemRepository.deleteByUserNo(userNo);
    }

    @Override
    public int totalPrice(Long userNo) {
        return cartItemRepository.findByUserNo(userNo).stream()
                .mapToInt(c -> c.getProduct().getPrice() * c.getQuantity())
                .sum();
    }

    private CartItem getOwned(Long userNo, Long cartItemNo) {
        CartItem item = cartItemRepository.findById(cartItemNo)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 항목을 찾을 수 없습니다."));
        if (!item.getUser().getNo().equals(userNo)) {
            throw new IllegalArgumentException("본인의 장바구니가 아닙니다.");
        }
        return item;
    }
}
