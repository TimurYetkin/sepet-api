package com.yetkin.sepet.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand", nullable = false, length = 100)
    private String brand;

    @Column(name = "category", nullable = false, length = 100)
    private String category;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "stock", nullable = false)
    private Long stock;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "second_free")
    private boolean secondFree;
}
