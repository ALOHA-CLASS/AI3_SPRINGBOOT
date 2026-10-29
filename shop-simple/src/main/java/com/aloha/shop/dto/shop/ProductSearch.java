package com.aloha.shop.dto.shop;

import lombok.Data;

/**
 * 상품 검색 조건 DTO (QueryDSL 동적 조건 + 페이징)
 */
@Data
public class ProductSearch {
    private String keyword;     // 상품명 키워드
    private String category;    // 카테고리
}
