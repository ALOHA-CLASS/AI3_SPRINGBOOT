package com.aloha.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ShopSimpleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopSimpleApplication.class, args);
	}

}
