package com.aloha.shop.service.shop;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloha.shop.domain.shop.CartItem;
import com.aloha.shop.domain.shop.OrderItem;
import com.aloha.shop.domain.shop.OrderStatus;
import com.aloha.shop.domain.shop.Orders;
import com.aloha.shop.domain.shop.Product;
import com.aloha.shop.domain.users.User;
import com.aloha.shop.dto.shop.OrderForm;
import com.aloha.shop.repository.shop.CartItemRepository;
import com.aloha.shop.repository.shop.OrderRepository;
import com.aloha.shop.repository.shop.ProductRepository;
import com.aloha.shop.repository.users.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Orders orderFromCart(Long userNo, OrderForm form) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        List<CartItem> cartItems = cartItemRepository.findByUserNo(userNo);
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("장바구니가 비어 있습니다.");
        }

        Orders order = Orders.builder()
                .orderNo(Orders.generateOrderNo())
                .user(user)
                .status(OrderStatus.ORDERED)
                .receiver(form.getReceiver())
                .phone(form.getPhone())
                .zipcode(form.getZipcode())
                .address1(form.getAddress1())
                .address2(form.getAddress2())
                .build();

        int total = 0;
        for (CartItem cart : cartItems) {
            Product product = cart.getProduct();

            // 재고 확인 및 차감
            if (product.getStock() < cart.getQuantity()) {
                throw new IllegalStateException("재고가 부족합니다: " + product.getName());
            }
            product.setStock(product.getStock() - cart.getQuantity());

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .productName(product.getName())
                    .price(product.getPrice())
                    .quantity(cart.getQuantity())
                    .build();
            order.addOrderItem(orderItem);
            total += orderItem.getSubtotal();
        }
        order.setTotalAmount(total);

        Orders saved = orderRepository.save(order);

        // 주문 완료 후 장바구니 비우기
        cartItemRepository.deleteByUserNo(userNo);

        return saved;
    }

    @Override
    @Transactional
    public Orders orderDirect(Long userNo, Long productNo, int quantity, OrderForm form) {
        if (quantity < 1) quantity = 1;

        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        Product product = productRepository.findById(productNo)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        if (product.getStock() < quantity) {
            throw new IllegalStateException("재고가 부족합니다: " + product.getName());
        }
        product.setStock(product.getStock() - quantity);

        Orders order = Orders.builder()
                .orderNo(Orders.generateOrderNo())
                .user(user)
                .status(OrderStatus.ORDERED)
                .receiver(form.getReceiver())
                .phone(form.getPhone())
                .zipcode(form.getZipcode())
                .address1(form.getAddress1())
                .address2(form.getAddress2())
                .build();

        OrderItem orderItem = OrderItem.builder()
                .product(product)
                .productName(product.getName())
                .price(product.getPrice())
                .quantity(quantity)
                .build();
        order.addOrderItem(orderItem);
        order.setTotalAmount(orderItem.getSubtotal());

        return orderRepository.save(order);
    }

    @Override
    public Page<Orders> list(Long userNo, Pageable pageable) {
        return orderRepository.findByUser(userNo, pageable);
    }

    @Override
    public Orders get(Long userNo, String orderNo) {
        Orders order = orderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
        if (!order.getUser().getNo().equals(userNo)) {
            throw new IllegalArgumentException("본인의 주문이 아닙니다.");
        }
        // 주문항목 지연로딩 초기화
        order.getOrderItems().size();
        return order;
    }

    @Override
    @Transactional
    public void cancel(Long userNo, String orderNo) {
        Orders order = get(userNo, orderNo);
        if (order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.SHIPPING) {
            throw new IllegalStateException("배송 중이거나 완료된 주문은 취소할 수 없습니다.");
        }
        // 재고 복원
        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
        }
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }
}
