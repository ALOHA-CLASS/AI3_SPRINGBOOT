package com.aloha.shop.repository.shop;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aloha.shop.domain.shop.Product;

/**
 * 상품 JPA 리포지토리 인터페이스
 * * ProductRepositoryCustom : QueryDSL을 사용한 커스텀 상품 검색 기능을 제공합니다.
 */
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
}
