package com.yetkin.sepet.service;

import com.yetkin.sepet.model.dto.BasketDTO;
import com.yetkin.sepet.model.dto.BasketSaveDTO;
import com.yetkin.sepet.model.dto.DiscountBasketDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IBasketService {
    List<BasketDTO> findAllByUser();

    List<DiscountBasketDTO> getDiscountProducts();

    BasketDTO findById(Long id);

    @Transactional
    BasketDTO addToBasket(BasketSaveDTO saveDTO);

    @Transactional
    Long delete(Long id);
}
