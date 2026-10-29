package com.aloha.shop.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

/**
 * 공통 감사(Auditing) 필드 - 등록/수정 일자
 */
@Getter
@Setter
@MappedSuperclass                                   // 다른 엔터티 클래스들이 상속받아 공통 변수를 가지도록 해준다.
@EntityListeners(AuditingEntityListener.class)      // JPA Auditing 기능을 사용하기 위해 엔터티 리스너를 등록한다.
public abstract class BaseEntity {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(unique = true, length = 36)
    private String id;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;   // 등록일자

    @LastModifiedDate
    private LocalDateTime updatedAt;   // 수정일자

    @PrePersist
    private void generateId() {
        if (this.id == null) {
        this.id = UUID.randomUUID().toString();
        }
    }
}
