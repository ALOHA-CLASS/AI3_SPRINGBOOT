package com.aloha.jpashop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@MappedSuperclass   // 이 클래스는 다른 엔티티 클래스들이 상속받아 공통 필드를 가지도록 하는 추상 클래스입니다.
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(unique = true, length = 36)     // VARCHAR(36)
    private String id;

    @Column(
        columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
        insertable = false,
        updatable = false
    )
    private LocalDateTime createdAt;

    @Column(
        columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",
        insertable = false,
        updatable = false
    )
    private LocalDateTime updatedAt;

    @PrePersist
    private void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}
