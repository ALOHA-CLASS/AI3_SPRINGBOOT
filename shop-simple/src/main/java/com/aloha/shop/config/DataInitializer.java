package com.aloha.shop.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.aloha.shop.domain.shop.Product;
import com.aloha.shop.domain.users.User;
import com.aloha.shop.repository.shop.ProductRepository;
import com.aloha.shop.repository.users.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * 최초 실행 시 샘플 데이터 시딩 (상품 + 데모 회원)
 */
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // 데모 회원 (user / 1234)
            if (userRepository.findByUsername("user").isEmpty()) {
                User user = User.builder()
                        .username("user")
                        .password(passwordEncoder.encode("123456"))
                        .name("알로하")
                        .email("user@aloha.com")
                        .phone("010-1234-5678")
                        .enabled(true)
                        .build();
                user.addAuth("ROLE_USER");
                // user.authList 에 username 세팅
                user.getAuthList().forEach(auth -> auth.setUsername(user.getUsername()));
                userRepository.save(user);
            }

            // 샘플 상품
            if (productRepository.count() == 0) {
                productRepository.save(product("나이키 프리 런", "신발", 129000, 50,
                        "가볍고 편안한 러닝화", "https://images.unsplash.com/photo-1552346154-21d32810aba3?w=600"));
                productRepository.save(product("데일리 백팩", "가방", 89000, 30,
                        "심플한 데일리 백팩", "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=600"));
                productRepository.save(product("미니멀 워치", "시계", 159000, 20,
                        "미니멀 디자인 손목시계", "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=600"));
                productRepository.save(product("무선 헤드폰", "음향", 199000, 40,
                        "노이즈 캔슬링 헤드폰", "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=600"));
                productRepository.save(product("라운드 선글라스", "액세서리", 79000, 60,
                        "클래식 라운드 선글라스", "https://images.unsplash.com/photo-1511499767150-a48a237f0083?w=600"));
                productRepository.save(product("레더 자켓", "의류", 259000, 15,
                        "빈티지 레더 자켓", "https://images.unsplash.com/photo-1551028719-00167b16eac5?w=600"));
                productRepository.save(product("폴라로이드 카메라", "가전", 149000, 25,
                        "즉석 인화 카메라", "https://images.unsplash.com/photo-1526170375885-4d8ecf77b99f?w=600"));
                productRepository.save(product("향수 No.5", "뷰티", 189000, 35,
                        "시그니처 향수", "https://images.unsplash.com/photo-1541643600914-78b084683601?w=600"));
            }
        };
    }

    private Product product(String name, String category, int price, int stock, String desc, String img) {
        return Product.builder()
                .name(name)
                .category(category)
                .price(price)
                .stock(stock)
                .description(desc)
                .imageUrl(img)
                .build();
    }
}
