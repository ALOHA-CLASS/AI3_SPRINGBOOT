package com.aloha.criteriaapi.repository; // 리포지토리 클래스들이 위치하는 패키지

import java.util.ArrayList; // 동적 조건(Predicate) 목록을 담을 가변 리스트
import java.util.List; // 조회 결과 및 조건 목록 타입

import org.springframework.stereotype.Repository; // 스프링 빈으로 등록하기 위한 어노테이션

import com.aloha.criteriaapi.domain.Product; // 조회 대상 엔티티

import jakarta.persistence.EntityManager; // JPA 영속성 컨텍스트 및 쿼리 실행 담당 객체
import jakarta.persistence.PersistenceContext; // EntityManager 주입 어노테이션
import jakarta.persistence.criteria.CriteriaBuilder; // Criteria API 쿼리 조립을 위한 빌더
import jakarta.persistence.criteria.CriteriaQuery; // Criteria 쿼리 객체
import jakarta.persistence.criteria.Predicate; // WHERE 절 조건을 표현하는 객체
import jakarta.persistence.criteria.Root; // 쿼리의 FROM 절에 해당하는 엔티티 루트

/**
 * ProductRepository 의 커스텀 구현체.
 * 이름 규칙(Repository 인터페이스명 + Impl)을 따르면 Spring Data JPA 가 자동으로 인식한다.
 */
@Repository // 스프링 빈으로 등록하여 다른 컴포넌트에서 주입받아 사용 가능하게 함
public class ProductRepositoryImpl implements ProductRepositoryCustom {

  @PersistenceContext // 스프링이 EntityManager 를 자동으로 주입
  private EntityManager em; // JPA 쿼리 생성 및 실행에 사용되는 매니저

  @Override
  public List<Product> findByNameContaining(String keyword) {
    CriteriaBuilder cb = em.getCriteriaBuilder(); // Criteria 쿼리 조립용 빌더 획득
    CriteriaQuery<Product> cq = cb.createQuery(Product.class); // Product 타입을 반환하는 쿼리 생성
    Root<Product> root = cq.from(Product.class); // Product 엔티티를 FROM 절 루트로 지정

    cq.select(root) // SELECT 대상은 Product 자체
      .where(cb.like(root.get("name"), "%" + keyword + "%")) // name 컬럼에 keyword 가 포함된 조건
      .orderBy(cb.desc(root.get("no"))); // no 컬럼 기준 내림차순 정렬

    return em.createQuery(cq).getResultList(); // 쿼리 실행 후 결과 리스트 반환
  }

  @Override
  public List<Product> findByPriceBetween(int minPrice, int maxPrice) {
    CriteriaBuilder cb = em.getCriteriaBuilder(); // Criteria 쿼리 조립용 빌더 획득
    CriteriaQuery<Product> cq = cb.createQuery(Product.class); // Product 타입을 반환하는 쿼리 생성
    Root<Product> root = cq.from(Product.class); // Product 엔티티를 FROM 절 루트로 지정

    cq.select(root) // SELECT 대상은 Product 자체
      .where(cb.between(root.get("price"), minPrice, maxPrice)) // price 가 minPrice ~ maxPrice 범위인 조건
      .orderBy(cb.asc(root.get("price"))); // price 기준 오름차순 정렬

    return em.createQuery(cq).getResultList(); // 쿼리 실행 후 결과 리스트 반환
  }

  @Override
  public List<Product> findAllOrderByPriceDesc() {
    CriteriaBuilder cb = em.getCriteriaBuilder(); // Criteria 쿼리 조립용 빌더 획득
    CriteriaQuery<Product> cq = cb.createQuery(Product.class); // Product 타입을 반환하는 쿼리 생성
    Root<Product> root = cq.from(Product.class); // Product 엔티티를 FROM 절 루트로 지정

    cq.select(root) // SELECT 대상은 Product 자체
      .orderBy(cb.desc(root.get("price"))); // price 기준 내림차순 정렬 (조건절 없이 전체 조회)

    return em.createQuery(cq).getResultList(); // 쿼리 실행 후 결과 리스트 반환
  }

  @Override
  public List<Product> searchByCriteria(String name, Integer minPrice, Integer maxPrice, Integer minStock) {
    CriteriaBuilder cb = em.getCriteriaBuilder(); // Criteria 쿼리 조립용 빌더 획득
    CriteriaQuery<Product> cq = cb.createQuery(Product.class); // Product 타입을 반환하는 쿼리 생성
    Root<Product> root = cq.from(Product.class); // Product 엔티티를 FROM 절 루트로 지정

    // 조건이 있는 항목만 Predicate 목록에 추가하는 동적 쿼리 방식
    List<Predicate> predicates = new ArrayList<>(); // 최종 WHERE 절에 들어갈 조건들을 누적할 리스트

    if (name != null && !name.isBlank()) { // name 파라미터가 존재할 때만 조건 추가
      predicates.add(cb.like(root.get("name"), "%" + name + "%")); // name 포함 검색 조건
    }
    if (minPrice != null) { // 최소 가격이 지정된 경우
      predicates.add(cb.ge(root.get("price"), minPrice)); // price >= minPrice 조건
    }
    if (maxPrice != null) { // 최대 가격이 지정된 경우
      predicates.add(cb.le(root.get("price"), maxPrice)); // price <= maxPrice 조건
    }
    if (minStock != null) { // 최소 재고가 지정된 경우
      predicates.add(cb.ge(root.get("stock"), minStock)); // stock >= minStock 조건
    }

    cq.select(root) // SELECT 대상은 Product 자체
      .where(predicates.toArray(new Predicate[0])) // 누적된 조건들을 AND 로 결합하여 WHERE 절 구성
      .orderBy(cb.desc(root.get("no"))); // no 컬럼 기준 내림차순 정렬

    return em.createQuery(cq).getResultList(); // 쿼리 실행 후 결과 리스트 반환
  }

}
