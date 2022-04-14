package com.yetkin.sepet.model.dto;

import com.yetkin.sepet.model.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BasketDTO {

    private Long id;

    private User user;

    private ProductDTO product;

    private Double totalPrice;

    private Long amount;

    private LocalDateTime createTime;
}
