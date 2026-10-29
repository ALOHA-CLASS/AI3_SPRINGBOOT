package com.aloha.criteriaapi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aloha.criteriaapi.domain.Product;
import com.aloha.criteriaapi.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

  private final ProductService productService;

  /**
   * 메인 화면
   * @param model
   * @return
   */
  @GetMapping("/")
  public String home(Model model) {
    model.addAttribute("productCount", productService.count());
    return "index";
  }

  /**
   * 상품 목록 화면 (Criteria API 동적 검색)
   * @param name 상품명 검색어
   * @param minPrice 최소 가격
   * @param maxPrice 최대 가격
   * @param minStock 최소 재고
   * @param model
   * @return
   */
  @GetMapping("/products")
  public String products(
      @RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "minPrice", required = false) Integer minPrice,
      @RequestParam(name = "maxPrice", required = false) Integer maxPrice,
      @RequestParam(name = "minStock", required = false) Integer minStock,
      Model model
  ) {
    List<Product> products = productService.search(name, minPrice, maxPrice, minStock);

    model.addAttribute("products", products);
    model.addAttribute("name", name);
    model.addAttribute("minPrice", minPrice);
    model.addAttribute("maxPrice", maxPrice);
    model.addAttribute("minStock", minStock);

    return "products";
  }

}
