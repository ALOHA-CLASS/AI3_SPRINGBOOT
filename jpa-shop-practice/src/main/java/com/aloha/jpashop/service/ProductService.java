package com.aloha.jpashop.service;

import com.aloha.jpashop.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    List<Product> search(String keyword);

    Product findById(Long id);

    long count();
}
