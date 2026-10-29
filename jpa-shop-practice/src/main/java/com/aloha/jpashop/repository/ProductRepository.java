package com.aloha.jpashop.repository;

import com.aloha.jpashop.entity.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.name LIKE CONCAT('%', :keyword, '%') ORDER BY p.no DESC")
    List<Product> search(@Param("keyword") String keyword);
}
