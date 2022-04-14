package com.yetkin.sepet.controller;

import com.yetkin.sepet.model.dto.ProductDTO;
import com.yetkin.sepet.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("api/product")//pre-path
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping() //api/product
    public List<ProductDTO> findAll() {
        return productService.findAll();
    }

    @PostMapping //api/product
    public ResponseEntity<?> saveProduct(@RequestBody @Valid ProductDTO productDTO) {
        return new ResponseEntity<>(productService.saveProduct(productDTO), HttpStatus.OK);
    }

    @PutMapping("/{id}") //api/product/{id}
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDTO productDTO) {
        return new ResponseEntity<>(productService.updateProduct(id, productDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}") //api/product/{id}
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return new ResponseEntity<>(productService.delete(id), HttpStatus.OK);
    }
}
