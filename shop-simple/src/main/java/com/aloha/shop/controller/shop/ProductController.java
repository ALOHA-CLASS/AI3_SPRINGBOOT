package com.aloha.shop.controller.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aloha.shop.domain.shop.Product;
import com.aloha.shop.dto.shop.ProductSearch;
import com.aloha.shop.service.shop.ProductService;

import lombok.RequiredArgsConstructor;

/**
 * 상품 - 목록/상세 (페이징 + QueryDSL 검색)
 */
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public String list(@ModelAttribute ProductSearch search,
                       @RequestParam(name = "page", defaultValue = "0") int page,
                       @RequestParam(name = "size", defaultValue = "9") int size,
                       Model model) {
        Page<Product> products = productService.list(search, PageRequest.of(page, size));
        model.addAttribute("products", products);
        model.addAttribute("search", search);
        // 스프링 URL
        // <a th:href="@{/products(page=${i}, size=${products.size}, count=${count})}"
        

        
        return "products/list";
    }

    @GetMapping("/{no}")
    public String detail(@PathVariable("no") Long no, Model model) {
        model.addAttribute("product", productService.get(no));
        return "products/detail";
    }
}
