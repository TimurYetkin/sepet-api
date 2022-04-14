package com.yetkin.sepet.controller;

import com.yetkin.sepet.model.dto.BasketDTO;
import com.yetkin.sepet.model.dto.BasketSaveDTO;
import com.yetkin.sepet.model.dto.DiscountBasketDTO;
import com.yetkin.sepet.service.IBasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("api/basket")//pre-path
public class BasketController {
    @Autowired
    private IBasketService basketService;

    @GetMapping() //api/basket
    public List<BasketDTO> findAll() {
        return basketService.findAllByUser();
    }

    @GetMapping("/calculate-discount") //api/basket/calculate-discount
    public List<DiscountBasketDTO> getDiscountProducts() {
        return basketService.getDiscountProducts();
    }

    @PostMapping //api/basket
    public ResponseEntity<?> addToBasket(@RequestBody @Valid BasketSaveDTO saveDTO) {
        return new ResponseEntity<>(basketService.addToBasket(saveDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}") //api/basket/{id}
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return new ResponseEntity<>(basketService.delete(id), HttpStatus.OK);
    }
}
