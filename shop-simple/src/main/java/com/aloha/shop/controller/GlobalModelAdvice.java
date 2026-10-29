package com.aloha.shop.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.aloha.shop.security.CustomUser;
import com.aloha.shop.service.shop.CartService;

import lombok.RequiredArgsConstructor;

/**
 * 모든 화면 공통 모델 - 헤더의 장바구니 뱃지 수량
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAdvice {

    private final CartService cartService;

    @ModelAttribute("cartCount")
    public long cartCount(@AuthenticationPrincipal CustomUser loginUser) {
        if (loginUser == null) {
            return 0;
        }
        return cartService.count(loginUser.getNo());
    }
}
