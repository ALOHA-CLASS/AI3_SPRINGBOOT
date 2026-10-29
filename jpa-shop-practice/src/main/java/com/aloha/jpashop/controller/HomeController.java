package com.aloha.jpashop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.aloha.jpashop.service.CartService;
import com.aloha.jpashop.service.OrderService;
import com.aloha.jpashop.service.ProductService;
import com.aloha.jpashop.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final ProductService productService;
    private final CartService cartService;
    private final OrderService orderService;

    /**
     * 메인 페이지
     * @param model
     * @return
     */
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("userCount", userService.count());
        model.addAttribute("productCount", productService.count());
        model.addAttribute("cartCount", cartService.count());
        model.addAttribute("orderCount", orderService.count());
        return "index";
    }

    /**
     * 사용자 목록 페이지
     * @param keyword
     * @param model
     * @return
     */
    @GetMapping("/users")
    public String users(@RequestParam(defaultValue = "") String keyword, Model model) {
        model.addAttribute("users", keyword.isBlank()
                ? userService.findAll()
                : userService.search(keyword));
        model.addAttribute("keyword", keyword);
        return "users";
    }

    /**
     * 상품 목록 페이지
     * @param keyword
     * @param model
     * @return
     */
    @GetMapping("/products")
    public String products(@RequestParam(defaultValue = "") String keyword, Model model) {
        model.addAttribute("products", keyword.isBlank()
                ? productService.findAll()
                : productService.search(keyword));
        model.addAttribute("keyword", keyword);
        return "products";
    }

    /**
     * 장바구니 페이지
     * @param userNo
     * @param model
     * @return
     */
    @GetMapping("/carts/{userNo}")
    public String cart(@PathVariable("userNo") Long userNo, Model model) {
        var cart = cartService.findByNo(userNo);
        model.addAttribute("cart", cart);
        model.addAttribute("user", userService.findByNo(userNo));
        return "cart";
    }

    /**
     * 주문 목록 페이지
     * @param userNo
     * @param model
     * @return
     */
    @GetMapping("/orders/{userNo}")
    public String orders(@PathVariable("userNo") Long userNo, Model model) {
        model.addAttribute("user", userService.findByNo(userNo));
        model.addAttribute("orders", orderService.findByNo(userNo));
        return "orders";
    }
}
