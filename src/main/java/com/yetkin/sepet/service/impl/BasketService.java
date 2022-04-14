package com.yetkin.sepet.service.impl;


import com.yetkin.sepet.exception.GenericException;
import com.yetkin.sepet.exception.errors.ErrorCode;
import com.yetkin.sepet.mapper.BasketMapper;
import com.yetkin.sepet.model.dto.BasketDTO;
import com.yetkin.sepet.model.dto.BasketSaveDTO;
import com.yetkin.sepet.model.dto.DiscountBasketDTO;
import com.yetkin.sepet.model.entity.Basket;
import com.yetkin.sepet.model.entity.Product;
import com.yetkin.sepet.model.entity.User;
import com.yetkin.sepet.model.enumerated.DiscountType;
import com.yetkin.sepet.repository.BasketRepository;
import com.yetkin.sepet.repository.ProductRepository;
import com.yetkin.sepet.repository.UserRepository;
import com.yetkin.sepet.service.IBasketService;
import com.yetkin.sepet.util.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class BasketService implements IBasketService {
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;
    private final BasketMapper basketMapper;
    private final UserRepository userRepository;

    @Override
    public List<BasketDTO> findAllByUser() {
        return findAllBasketByUser().stream().map(basketMapper::toDTO).collect(Collectors.toList());
    }

    public List<Basket> findAllBasketByUser() {
        return basketRepository.findByUserId(SecurityUtils.getCurrentUser().getId());
    }

    @Override
    public List<DiscountBasketDTO> getDiscountProducts() {
        List<Basket> basketList = findAllBasketByUser();
        List<DiscountBasketDTO> dtoList = new ArrayList<>();
        boolean overOneTousend = basketList.stream().mapToDouble(b -> b.getTotalPrice()).sum() >= 1000d;
        Double discountedAmount = 0d;
        Double minTotalPrice = 0d;
        DiscountType discountType = null;
        int discountIndex = 0;
        int minTotalPriceIndex = 0;

        int index = 0;

        for (Basket basket : basketList) {
            DiscountBasketDTO dto = basketMapper.toDiscountDTO(basket);
            //bir alana bir bedava kontrolü yapılıyor
            if (basket.getProduct().isSecondFree()) {
                if ((dto.getTotalPrice() / 2) > discountedAmount) {
                    discountedAmount = dto.getTotalPrice() / 2;
                    discountType = DiscountType.SECOND_FREE;
                    discountIndex = index;
                }
            }
            //3 ve üzeri alana %15 indirim kontrolü yapılıyor.
            if (basket.getAmount() >= 3) {
                if (dto.getTotalPrice() * 0.15 > discountedAmount) {
                    discountedAmount = dto.getTotalPrice() * 0.15;
                    discountType = DiscountType.THIRD_ONE_DISCOUNT;
                    discountIndex = index;
                }
            }
            //1000 tl ve üzeri indirim için En düşük fiyat tespit ediliyor
            if (overOneTousend && basket.getTotalPrice() <= minTotalPrice) {
                minTotalPrice = basket.getTotalPrice();
                minTotalPriceIndex = index;
            }
            dto.setDiscountedPrice(dto.getTotalPrice());
            dtoList.add(dto);
            index++;
        }
        //1000 tl üzerindeki indirim en düşük fiyata uygulanıyordu. Bu indirim diğerlerinden büyükse buna göre işlem yapılacak.
        if (overOneTousend) {
            if (minTotalPrice * 0.2 > discountedAmount) {
                discountedAmount = minTotalPrice * 0.2;
                discountType = DiscountType.OVER_ONE_TOUSEND;
                discountIndex = minTotalPriceIndex;
            }
        }
        if (discountType != null) {
            dtoList.get(discountIndex).setDiscountType(discountType);
            dtoList.get(discountIndex).setDiscountedPrice(dtoList.get(discountIndex).getDiscountedPrice() - discountedAmount);
        }
        return dtoList;
    }

    @Override
    public BasketDTO findById(Long id) {
        return basketMapper.toDTO(basketRepository.findById(id)
                .orElseThrow(() -> new GenericException(ErrorCode.ENTITY_NOT_FOUND_EXCEPTION, id, Basket.class.getSimpleName())));
    }

    @Override
    @Transactional
    public BasketDTO addToBasket(BasketSaveDTO saveDTO) {
        Product product = productRepository.findById(saveDTO.getProductId())
                .orElseThrow(() -> new GenericException(ErrorCode.ENTITY_NOT_FOUND_EXCEPTION, saveDTO.getProductId(), Product.class.getSimpleName()));
        if (saveDTO.getAmount().compareTo(product.getStock()) > 0)
            throw new GenericException(ErrorCode.NOT_ENOUGH_STOCK);
        User user = userRepository.findByUsername(SecurityUtils.getCurrentUser().getUsername())
                .orElseThrow(() -> new GenericException(ErrorCode.ENTITY_NOT_FOUND_EXCEPTION, SecurityUtils.getCurrentUser().getUsername(), User.class.getSimpleName()));
        Basket basket = new Basket();
        if (basketRepository.countByUserIdAndProductIdAndPurshased(user.getId(), product.getId(), false) > 0) {
            basket = basketRepository.findByUserIdAndProductIdAndPurshased(user.getId(), product.getId(), false).get(0);
            basket.setAmount(basket.getAmount() + saveDTO.getAmount());
        } else {
            basket.setProduct(product);
            basket.setUser(user);
            basket.setAmount(saveDTO.getAmount());
        }
        basket.setPurshased(false);
        basket.setTotalPrice(basket.getAmount() * product.getPrice());
        basket.setCreateTime(LocalDateTime.now());

        return basketMapper.toDTO(basketRepository.save(basket));
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        basketRepository.deleteById(id);
        return id;
    }
}
