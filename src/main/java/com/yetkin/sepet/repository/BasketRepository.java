package com.yetkin.sepet.repository;

import com.yetkin.sepet.model.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    Long countByUserIdAndProductIdAndPurshased(Long userId, Long productId, boolean purshased);

    List<Basket> findByUserIdAndProductIdAndPurshased(Long userId, Long productId, boolean purshased);

    List<Basket> findByUserId(Long userId);
}

