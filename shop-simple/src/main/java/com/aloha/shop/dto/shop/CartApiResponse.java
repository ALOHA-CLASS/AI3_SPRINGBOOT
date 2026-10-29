package com.aloha.shop.dto.shop;

/**
 * 장바구니 fetch(JSON) 응답 - 변경된 항목/전체 합계 정보
 */
public record CartApiResponse(Long no, int quantity, int itemTotal, int cartTotal, long cartCount) {
}
