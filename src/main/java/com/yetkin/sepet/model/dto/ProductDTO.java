package com.yetkin.sepet.model.dto;

import com.sun.istack.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDTO {

    private Long id;

    @NotNull
    private String brand;

    @NotNull
    private String category;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Double price;

    @NotNull
    private Long stock;

    @NotNull
    private LocalDateTime createTime;

    private boolean secondFree;

}
