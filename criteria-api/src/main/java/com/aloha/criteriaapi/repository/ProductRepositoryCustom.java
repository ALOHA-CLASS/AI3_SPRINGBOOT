package com.aloha.criteriaapi.repository;

import java.util.List;

import com.aloha.criteriaapi.domain.Product;

/**
 * JPA Criteria API 를 이용한 커스텀 조회 기능을 정의한다.
 */
public interface ProductRepositoryCustom {

  /**
   * 상품명 부분 검색 (LIKE)
   * @param keyword 검색어
   * @return 상품 목록
   */
  List<Product> findByNameContaining(String keyword);

  /**
   * 가격 범위 검색 (BETWEEN)
   * @param minPrice 최소 가격
   * @param maxPrice 최대 가격
   * @return 상품 목록
   */
  List<Product> findByPriceBetween(int minPrice, int maxPrice);

  /**
   * 가격 내림차순 전체 조회 (ORDER BY)
   * @return 상품 목록
   */
  List<Product> findAllOrderByPriceDesc();

  /**
   * 상품명 / 최소가격 / 최대가격 / 최소재고 조건을 조합한 동적 검색.
   * 각 파라미터가 null 이거나 비어있으면 해당 조건은 무시된다.
   * @param name 상품명 (부분 일치)
   * @param minPrice 최소 가격
   * @param maxPrice 최대 가격
   * @param minStock 최소 재고
   * @return 상품 목록
   */
  List<Product> searchByCriteria(String name, Integer minPrice, Integer maxPrice, Integer minStock);

}
