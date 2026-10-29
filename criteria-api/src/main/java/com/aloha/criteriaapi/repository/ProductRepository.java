package com.aloha.criteriaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aloha.criteriaapi.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
}
