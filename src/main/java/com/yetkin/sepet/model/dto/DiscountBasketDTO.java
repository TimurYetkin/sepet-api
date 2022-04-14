package com.yetkin.sepet.model.dto;

import com.yetkin.sepet.model.entity.User;
import com.yetkin.sepet.model.enumerated.DiscountType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DiscountBasketDTO {

    private Long id;

    private User user;

    private ProductDTO product;

    private Double totalPrice;

    private Double discountedPrice;

    private DiscountType discountType;

    private Long amount;

    private LocalDateTime createTime;
}
