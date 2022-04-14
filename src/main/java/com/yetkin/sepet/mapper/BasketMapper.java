package com.yetkin.sepet.mapper;


import com.yetkin.sepet.model.dto.BasketDTO;
import com.yetkin.sepet.model.dto.DiscountBasketDTO;
import com.yetkin.sepet.model.entity.Basket;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BasketMapper {
    BasketDTO toDTO(Basket basket);

    DiscountBasketDTO toDiscountDTO(Basket basket);

    List<BasketDTO> toListDTO(List<Basket> basketList);

    Basket toEntity(BasketDTO dto);

    List<Basket> toListEntity(List<BasketDTO> dtoList);

}
