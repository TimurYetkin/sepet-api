package com.yetkin.sepet.mapper;


import com.yetkin.sepet.model.dto.ProductDTO;
import com.yetkin.sepet.model.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);

    List<ProductDTO> toListDTO(List<Product> product);

    Product toEntity(ProductDTO dto);

    List<Product> toListEntity(List<ProductDTO> dtoList);

}
