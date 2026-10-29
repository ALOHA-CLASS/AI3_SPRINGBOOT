package com.aloha.shop.dto.shop;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 주문서 작성 폼 DTO (장바구니 → 주문)
 */
@Data
public class OrderForm {

    @NotBlank(message = "받는 분을 입력하세요.")
    private String receiver;

    @NotBlank(message = "연락처를 입력하세요.")
    private String phone;

    private String zipcode;

    @NotBlank(message = "주소를 입력하세요.")
    private String address1;

    private String address2;
}
