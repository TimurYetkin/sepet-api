package com.yetkin.sepet.service;

import com.yetkin.sepet.model.dto.ProductDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IProductService {
    List<ProductDTO> findAll();

    ProductDTO findById(Long id);

    @Transactional
    ProductDTO saveProduct(ProductDTO productDTO);

    @Transactional
    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    Long delete(Long id);
}
