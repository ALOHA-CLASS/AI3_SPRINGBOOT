package com.aloha.shop.service.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloha.shop.domain.shop.Product;
import com.aloha.shop.dto.shop.ProductSearch;
import com.aloha.shop.repository.shop.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Page<Product> list(ProductSearch search, Pageable pageable) {
        return productRepository.search(search, pageable);
    }

    @Override
    public Product get(Long no) {
        return productRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }

    @Override
    public Product select(String id) {
        return productRepository.findById(id);
    }
}
