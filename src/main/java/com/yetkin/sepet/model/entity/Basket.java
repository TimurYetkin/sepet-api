package com.yetkin.sepet.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "totalPrice", nullable = false)
    private Double totalPrice;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "purshased", nullable = false)
    private boolean purshased;
}
