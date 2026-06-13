package com.pup.taguig.ordermanagement.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pup.taguig.ordermanagement.dto.ProductRequestDTO;
import com.pup.taguig.ordermanagement.dto.ProductResponseDTO;
import com.pup.taguig.ordermanagement.model.Product;
import com.pup.taguig.ordermanagement.repository.ProductRepository;
import com.pup.taguig.ordermanagement.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public ProductResponseDTO createProduct(ProductRequestDTO request) {
		if (request.getPrice() == null || request.getPrice().compareTo(BigDecimal.ZERO) <= 0 || request.getStock() < 0) {
			return null; 
		}

		Product product = new Product();
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setStock(request.getStock());

		Product savedProduct = productRepository.save(product);
		return toDTO(savedProduct);
	}

	@Override
	public List<ProductResponseDTO> getAllProducts(Integer page, Integer size) {
		List<Product> products;
		
		if (page != null && size != null) {
			Pageable pageable = PageRequest.of(page, size);
			products = productRepository.findAll(pageable).getContent();
		} else {
			products = productRepository.findAll();
		}
		
		List<ProductResponseDTO> responseList = new ArrayList<>();
		for (Product p : products) {
			responseList.add(toDTO(p));
		}
		
		return responseList;
	}

	@Override
	public ProductResponseDTO getProductById(Long id) {
		Product product = productRepository.findById(id).orElse(null);
		if (product == null) {
			return null;
		}
		return toDTO(product);
	}

	private ProductResponseDTO toDTO(Product product) {
		ProductResponseDTO dto = new ProductResponseDTO();
		dto.setId(product.getId());
		dto.setName(product.getName());
		dto.setDescription(product.getDescription());
		dto.setPrice(product.getPrice());
		dto.setStock(product.getStock());
		return dto;
	}
}