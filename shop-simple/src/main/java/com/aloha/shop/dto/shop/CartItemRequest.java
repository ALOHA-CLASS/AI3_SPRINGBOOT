package com.aloha.shop.dto.shop;

/**
 * 장바구니 담기/수량변경 - fetch(JSON) 요청 바디
 */
public record CartItemRequest(Long productNo, Integer quantity) {
}
