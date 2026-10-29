package com.aloha.shop.domain.shop;

/**
 * 주문 상태
 */
public enum OrderStatus {
    ORDERED("주문완료"),
    PAID("결제완료"),
    SHIPPING("배송중"),
    DELIVERED("배송완료"),
    CANCELED("주문취소");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
