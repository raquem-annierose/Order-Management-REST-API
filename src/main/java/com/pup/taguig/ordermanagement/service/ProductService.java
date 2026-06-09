package com.pup.taguig.ordermanagement.service;

import java.util.List;
import com.pup.taguig.ordermanagement.dto.ProductRequestDTO;
import com.pup.taguig.ordermanagement.dto.ProductResponseDTO;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO request);
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO getProductById(Long id);
}