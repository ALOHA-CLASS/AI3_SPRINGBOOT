package com.aloha.shop.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.aloha.shop.dto.shop.ProductSearch;
import com.aloha.shop.service.shop.ProductService;

import lombok.RequiredArgsConstructor;

/**
 * 메인 홈
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        // 최신 상품 8개 노출
        var products = productService.list(new ProductSearch(), PageRequest.of(0, 8)).getContent();
        model.addAttribute("products", products);
        return "index";
    }
}
