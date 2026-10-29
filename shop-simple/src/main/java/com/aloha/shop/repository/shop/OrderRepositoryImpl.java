package com.aloha.shop.repository.shop;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.aloha.shop.domain.shop.Orders;
import com.aloha.shop.domain.shop.QOrders;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

/**
 * QueryDSL 주문 목록 조회 구현체 (회원별 + 페이징)
 */
@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    // JPAQueryFactory를 주입받아 QueryDSL을 사용합니다.
    private final JPAQueryFactory queryFactory;

    /**    
     * Pageable : 페이징 정보를 담고 있는 객체 (페이지 번호, 페이지 크기 등)
     * @see com.aloha.shop.repository.shop.OrderRepositoryCustom#findByUser(java.lang.Long, org.springframework.data.domain.Pageable)
     */
    @Override
    public Page<Orders> findByUser(Long userNo, Pageable pageable) {
        // 회원별 주문 목록 조회 (페이징 포함)
        QOrders orders = QOrders.orders;

        // 페이징 처리된 주문 목록 조회
        /*
            QueryDSL을 사용하여 회원별 주문 목록을 페이징 처리하여 조회합니다.
            SELECT * 
            FROM orders 
            WHERE user_no = :userNo 
            ORDER BY no DESC LIMIT :limit OFFSET :offset
         */
        List<Orders> content = queryFactory
                .selectFrom(orders)
                .where(orders.user.no.eq(userNo))
                .orderBy(orders.no.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 주문 수 조회 (페이징 처리에 필요)
        /*
            QueryDSL을 사용하여 회원별 전체 주문 수를 조회합니다.
            SELECT COUNT(*) 
            FROM orders 
            WHERE user_no = :userNo
         */
        Long total = queryFactory
                .select(orders.count())
                .from(orders)
                .where(orders.user.no.eq(userNo))
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }
}
