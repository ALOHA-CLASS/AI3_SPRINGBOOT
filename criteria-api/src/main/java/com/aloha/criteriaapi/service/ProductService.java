package com.aloha.criteriaapi.service;

import java.util.List;

import com.aloha.criteriaapi.domain.Product;

public interface ProductService {
  List<Product> findAll();
  Product findByNo(Long no);
  long count();

  /**
   * 상품명 / 최소가격 / 최대가격 / 최소재고 조건으로 동적 검색 (Criteria API)
   */
  List<Product> search(String name, Integer minPrice, Integer maxPrice, Integer minStock);
}
