package com.aloha.shop.controller.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aloha.shop.domain.shop.Orders;
import com.aloha.shop.domain.shop.Product;
import com.aloha.shop.dto.shop.OrderForm;
import com.aloha.shop.security.CustomUser;
import com.aloha.shop.service.shop.CartService;
import com.aloha.shop.service.shop.OrderService;
import com.aloha.shop.service.shop.ProductService;
import com.aloha.shop.service.users.AddressService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 주문 - 주문서/주문처리/주문목록(페이징)/상세/취소
 */
@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;
    private final ProductService productService;
    private final AddressService addressService;

    // 주문서 작성 화면 (장바구니 기반)
    @GetMapping("/checkout")
    public String checkout(@AuthenticationPrincipal CustomUser loginUser, Model model) {
        Long userNo = loginUser.getNo();
        if (cartService.count(userNo) == 0) {
            return "redirect:/cart";
        }
        model.addAttribute("items", cartService.listAll(userNo));
        model.addAttribute("totalPrice", cartService.totalPrice(userNo));
        model.addAttribute("addresses", addressService.list(userNo));
        model.addAttribute("orderForm", new OrderForm());
        return "orders/checkout";
    }

    // 주문서 작성 화면 (상품 상세에서 바로 주문, 장바구니 미사용)
    @GetMapping("/checkout/direct")
    public String checkoutDirect(@AuthenticationPrincipal CustomUser loginUser,
                                 @RequestParam("productNo") Long productNo,
                                 @RequestParam(name = "quantity", defaultValue = "1") int quantity,
                                 Model model) {
        Long userNo = loginUser.getNo();
        if (quantity < 1) quantity = 1;
        Product product = productService.get(productNo);
        model.addAttribute("directProduct", product);
        model.addAttribute("directQuantity", quantity);
        model.addAttribute("totalPrice", product.getPrice() * quantity);
        model.addAttribute("addresses", addressService.list(userNo));
        model.addAttribute("orderForm", new OrderForm());
        return "orders/checkout";
    }

    // 주문 처리
    @PostMapping
    public String order(@AuthenticationPrincipal CustomUser loginUser,
                        @RequestParam(name = "productNo", required = false) Long productNo,
                        @RequestParam(name = "quantity", required = false) Integer quantity,
                        @Valid @ModelAttribute OrderForm orderForm,
                        BindingResult bindingResult,
                        Model model,
                        RedirectAttributes ra) {
        Long userNo = loginUser.getNo();
        boolean direct = productNo != null;
        if (bindingResult.hasErrors()) {
            if (direct) {
                Product product = productService.get(productNo);
                int qty = (quantity != null && quantity > 0) ? quantity : 1;
                model.addAttribute("directProduct", product);
                model.addAttribute("directQuantity", qty);
                model.addAttribute("totalPrice", product.getPrice() * qty);
            } else {
                model.addAttribute("items", cartService.listAll(userNo));
                model.addAttribute("totalPrice", cartService.totalPrice(userNo));
            }
            model.addAttribute("addresses", addressService.list(userNo));
            return "orders/checkout";
        }
        Orders order = direct
                ? orderService.orderDirect(userNo, productNo, (quantity != null && quantity > 0) ? quantity : 1, orderForm)
                : orderService.orderFromCart(userNo, orderForm);
        ra.addFlashAttribute("message", "주문이 완료되었습니다.");
        return "redirect:/orders/" + order.getOrderNo();
    }

    // 주문 목록 (페이징)
    @GetMapping
    public String list(@AuthenticationPrincipal CustomUser loginUser,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "5") int size,
                       Model model) {
        Page<Orders> orders = orderService.list(loginUser.getNo(), PageRequest.of(page, size));
        model.addAttribute("orders", orders);
        return "orders/list";
    }

    // 주문 상세
    @GetMapping("/{orderNo}")
    public String detail(@AuthenticationPrincipal CustomUser loginUser,
                         @PathVariable("orderNo") String orderNo,
                         Model model) {
        model.addAttribute("order", orderService.get(loginUser.getNo(), orderNo));
        return "orders/detail";
    }

    // 주문 취소
    @PostMapping("/{orderNo}/cancel")
    public String cancel(@AuthenticationPrincipal CustomUser loginUser,
                         @PathVariable("orderNo") String orderNo,
                         RedirectAttributes ra) {
        orderService.cancel(loginUser.getNo(), orderNo);
        ra.addFlashAttribute("message", "주문이 취소되었습니다.");
        return "redirect:/orders/" + orderNo;
    }
}
