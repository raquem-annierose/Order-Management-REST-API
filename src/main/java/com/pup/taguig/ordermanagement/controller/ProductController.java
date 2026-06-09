package com.pup.taguig.ordermanagement.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pup.taguig.ordermanagement.dto.ProductRequestDTO;
import com.pup.taguig.ordermanagement.dto.ProductResponseDTO;
import com.pup.taguig.ordermanagement.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	// User Story 4: List Products
	@GetMapping
	public List<ProductResponseDTO> getAllProducts() {
		return productService.getAllProducts();
	}
	
	// User Story 5: Get Product by ID
	@GetMapping("/{id}")
	public ProductResponseDTO getProductById(@PathVariable Long id) {
		return productService.getProductById(id);
	}
	
	// User Story 3: Create Product
	@PostMapping
	public ProductResponseDTO createProduct(@RequestBody ProductRequestDTO product) {
		ProductResponseDTO result = null;
		if (Objects.nonNull(product)) {
			result = productService.createProduct(product);
		}
		return result;
	}
}