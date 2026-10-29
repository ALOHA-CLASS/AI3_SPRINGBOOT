package com.aloha.jpashop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private int price;
    private int stock;
}
