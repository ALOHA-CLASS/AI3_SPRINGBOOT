package com.aloha.shop.controller.shop;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aloha.shop.domain.shop.CartItem;
import com.aloha.shop.dto.shop.CartApiResponse;
import com.aloha.shop.dto.shop.CartItemRequest;
import com.aloha.shop.security.CustomUser;
import com.aloha.shop.service.shop.CartService;

import lombok.RequiredArgsConstructor;

/**
 * 장바구니 - 목록(페이징)/담기/수량변경/삭제
 */
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public String list(@AuthenticationPrincipal CustomUser loginUser,
                       @RequestParam(name = "page", defaultValue = "0") int page,
                       @RequestParam(name = "size", defaultValue = "5") int size,
                       Model model) {
        Long userNo = loginUser.getNo();
        Page<CartItem> items = cartService.list(userNo, PageRequest.of(page, size));
        model.addAttribute("items", items);
        model.addAttribute("totalPrice", cartService.totalPrice(userNo));
        return "cart/list";
    }

    @PostMapping("/add")
    public String add(@AuthenticationPrincipal CustomUser loginUser,
                      @RequestParam(name = "productNo") Long productNo,
                      @RequestParam(name = "quantity", defaultValue = "1") int quantity) {
        cartService.addToCart(loginUser.getNo(), productNo, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/{no}/update")
    public String update(@AuthenticationPrincipal CustomUser loginUser,
                         @PathVariable("no") Long no,
                         @RequestParam(name = "quantity") int quantity) {
        cartService.updateQuantity(loginUser.getNo(), no, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/{no}/delete")
    public String delete(@AuthenticationPrincipal CustomUser loginUser,
                         @PathVariable("no") Long no) {
        cartService.remove(loginUser.getNo(), no);
        return "redirect:/cart";
    }

    // ========== 비동기 fetch(JSON) 전용 API : 담기(POST) / 수량변경(PUT) / 삭제(DELETE) ==========

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> addApi(@AuthenticationPrincipal CustomUser loginUser,
                                    @RequestBody CartItemRequest request) {
        try {
            Long userNo = loginUser.getNo();
            int quantity = request.quantity() == null ? 1 : request.quantity();
            cartService.addToCart(userNo, request.productNo(), quantity);
            return ResponseEntity.ok(toResponse(userNo, null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping(value = "/{no}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> updateApi(@AuthenticationPrincipal CustomUser loginUser,
                                       @PathVariable("no") Long no,
                                       @RequestBody CartItemRequest request) {
        try {
            Long userNo = loginUser.getNo();
            CartItem item = cartService.updateQuantity(userNo, no, request.quantity());
            return ResponseEntity.ok(toResponse(userNo, item));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{no}")
    @ResponseBody
    public ResponseEntity<?> deleteApi(@AuthenticationPrincipal CustomUser loginUser,
                                       @PathVariable("no") Long no) {
        try {
            Long userNo = loginUser.getNo();
            cartService.remove(userNo, no);
            return ResponseEntity.ok(toResponse(userNo, null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // 삭제/담기 후에는 item == null (해당 항목의 개별 합계는 필요 없음)
    private CartApiResponse toResponse(Long userNo, CartItem item) {
        int itemTotal = item != null ? item.getProduct().getPrice() * item.getQuantity() : 0;
        return new CartApiResponse(
                item != null ? item.getNo() : null,
                item != null ? item.getQuantity() : 0,
                itemTotal,
                cartService.totalPrice(userNo),
                cartService.count(userNo));
    }
}
