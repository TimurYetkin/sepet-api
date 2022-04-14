package com.yetkin.sepet.service.impl;


import com.yetkin.sepet.exception.GenericException;
import com.yetkin.sepet.exception.errors.ErrorCode;
import com.yetkin.sepet.mapper.ProductMapper;
import com.yetkin.sepet.model.dto.ProductDTO;
import com.yetkin.sepet.model.entity.Product;
import com.yetkin.sepet.repository.ProductRepository;
import com.yetkin.sepet.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(productMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ProductDTO findById(Long id) {
        return productMapper.toDTO(productRepository.findById(id)
                .orElseThrow(() -> new GenericException(ErrorCode.ENTITY_NOT_FOUND_EXCEPTION, id, Product.class.getSimpleName())));
    }

    @Override
    @Transactional
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new GenericException(ErrorCode.ENTITY_NOT_FOUND_EXCEPTION, id, Product.class.getSimpleName()));
        product.setBrand(productDTO.getBrand());
        product.setCategory(productDTO.getCategory());
        product.setDescription(productDTO.getDescription());
        product.setName(productDTO.getName());
        product.setStock(productDTO.getStock());
        product.setPrice(productDTO.getPrice());
        product.setCreateTime(LocalDateTime.now());
        product.setSecondFree(productDTO.isSecondFree());
        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public Long delete(Long id) {
        productRepository.deleteById(id);
        return id;
    }
}
