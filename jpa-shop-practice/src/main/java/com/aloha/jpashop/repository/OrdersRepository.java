package com.aloha.jpashop.repository;

import com.aloha.jpashop.entity.Orders;
import com.aloha.jpashop.entity.OrderStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query("SELECT o FROM Orders o WHERE o.user.no = :userId ORDER BY o.no DESC")
    List<Orders> findByUserId(@Param("userId") Long userId);

    @Query("SELECT o FROM Orders o JOIN FETCH o.user WHERE o.no = :id")
    Optional<Orders> findDetail(@Param("id") Long id);

    @Query("SELECT o.status, COUNT(o) FROM Orders o GROUP BY o.status")
    List<Object[]> countByStatus();

    List<Orders> findByStatus(OrderStatus status);
}
