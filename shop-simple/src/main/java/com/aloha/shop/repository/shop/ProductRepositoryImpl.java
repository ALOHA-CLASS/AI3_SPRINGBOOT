package com.aloha.shop.repository.shop;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.aloha.shop.domain.shop.Product;
import com.aloha.shop.domain.shop.QProduct;
import com.aloha.shop.dto.shop.ProductSearch;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

/**
 * QueryDSL 상품 검색 구현체 (동적 조건 + 페이징)
 */
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    // JPAQueryFactory를 주입받아 QueryDSL을 사용합니다.
    private final JPAQueryFactory queryFactory;

    /**    
     * Pageable : 페이징 정보를 담고 있는 객체 (페이지 번호, 페이지 크기 등)
     * @see com.aloha.shop.repository.shop.ProductRepositoryCustom#search(com.aloha.shop.dto.shop.ProductSearch, org.springframework.data.domain.Pageable)
     */
    @Override
    public Page<Product> search(ProductSearch search, Pageable pageable) {
        QProduct product = QProduct.product;

        // 동적 조건
        BooleanBuilder where = new BooleanBuilder();
        if (search != null) {
            if (StringUtils.hasText(search.getKeyword())) {
                // 상품 이름에 키워드가 포함된 경우만 조회
                // WHERE product.name LIKE %:keyword% OR product.description LIKE %:keyword%
                where.and(product.name.containsIgnoreCase(search.getKeyword())
                        .or(product.description.containsIgnoreCase(search.getKeyword())));
            }
            if (StringUtils.hasText(search.getCategory())) {
                // 상품 카테고리가 지정된 경우만 조회
                // WHERE product.category = :category
                where.and(product.category.eq(search.getCategory()));
            }
        }

        // 데이터 조회
        /*
            SELECT *
            FROM product
            WHERE 1=1
            <조건>
            ORDER BY no DESC
            LIMIT :limit OFFSET :offset
        */
        List<Product> content = queryFactory
                .selectFrom(product)
                .where(where)
                .orderBy(product.no.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 카운트
        /*
            SELECT COUNT(*)
            FROM product
            WHERE 1=1
            <조건>
        */
        Long total = queryFactory
                .select(product.count())
                .from(product)
                .where(where)
                .fetchOne();

        // QueryDSL을 사용하여 동적 조건에 맞는 전체 상품 수를 조회합니다.
        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }

    @Override
    public Product findById(String id) {
        QProduct product = QProduct.product;
        return queryFactory
                .selectFrom(product)
                .where(product.id.eq(id))
                .fetchOne();
    }
}
