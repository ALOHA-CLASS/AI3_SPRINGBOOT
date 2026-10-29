package com.aloha.shop.service.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aloha.shop.domain.shop.Product;
import com.aloha.shop.dto.shop.ProductSearch;

public interface ProductService {

    Page<Product> list(ProductSearch search, Pageable pageable);

    Product get(Long no);

    // 상품 상세 - id
    Product select(String id);
}
