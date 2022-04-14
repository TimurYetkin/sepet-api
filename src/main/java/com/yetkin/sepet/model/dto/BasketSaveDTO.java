package com.yetkin.sepet.model.dto;

import lombok.Data;

@Data
public class BasketSaveDTO {

    private Long id;

    private Long productId;

    private Long amount;
}
