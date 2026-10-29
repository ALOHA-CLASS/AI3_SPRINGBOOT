package com.aloha.shop.repository.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aloha.shop.domain.shop.Product;
import com.aloha.shop.dto.shop.ProductSearch;

/**
 * QueryDSL 커스텀 리포지토리 인터페이스
 */
public interface ProductRepositoryCustom {

    /**
     * 상품 검색 조건에 따라 페이징 처리된 상품 목록을 조회합니다.
     * SELECT *
     * FROM product
     * WHERE <검색 조건>
     * ORDER BY <정렬 조건>
     * @param search
     * @param pageable
     * @return
     */
    Page<Product> search(ProductSearch search, Pageable pageable);


    // 상품id 로 조회
    Product findById(String id);
}
