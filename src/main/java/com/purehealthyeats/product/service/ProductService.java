package com.purehealthyeats.product.service;

import java.util.List;

import com.purehealthyeats.product.dto.ProductDTO;

public interface ProductService {
    List<ProductDTO> findAllProducts();
    ProductDTO findProductById(Long id);
}
