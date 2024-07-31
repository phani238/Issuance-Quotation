package com.zurich.policy.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zurich.policy.entity.Product;
import com.zurich.policy.exception.BadRequestException;
import com.zurich.policy.exception.ResourceNotFoundException;
import com.zurich.policy.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api")
@EnableRetry
public class ProductController {

	@Autowired
	ProductService productService;

	@Operation(summary = "List of Products", description = "Listed all products from the core system.")
	@ApiResponse(responseCode = "201", description = "Successfully listed all the products")
	@GetMapping("/v1/products")
	public ResponseEntity<List<Product>> getProducts() {
		List<Product> policies = productService.getProducts();
		if (!policies.isEmpty()) {
			return new ResponseEntity<>(policies, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("No Data Available");
		}
	}

	@Operation(summary = "Listed Selected Product", description = "Listed the selected product issued from the core system.")
	@ApiResponse(responseCode = "201", description = "Successfully listed the selected product")
	@GetMapping("/v1/product")
	public ResponseEntity<Product> getProduct(@RequestParam(required = true) String productId) {
		if (!productId.isEmpty() || !productId.isBlank()) {
			Optional<Product> policy = productService.getProduct(Long.parseLong(productId));
			if (policy.isPresent()) {
				return new ResponseEntity<>(policy.get(), HttpStatus.OK);
			} else {
				throw new ResourceNotFoundException("No Record Found with ProductId = " + productId);
			}
		} else {
			throw new BadRequestException("Missing Request Parameter");
		}
	}

	@Operation(summary = "Saving Product", description = "Saving product to the core system.")
	@ApiResponse(responseCode = "201", description = "Successfully saved the product")
	@PostMapping("/v1/product")
	public ResponseEntity<Product> saveproduct(@RequestBody Product product) {
		if (product != null) {
			productService.saveOrUpdateProduct(product);
			return ResponseEntity.status(HttpStatus.CREATED).body(product);
		} else {
			throw new BadRequestException("Missing Request Body");
		}
	}

	@Operation(summary = "Updating Product", description = "Updating product to the core system.")
	@ApiResponse(responseCode = "201", description = "Successfully updated the product")
	@PutMapping("/v1/product")
	public ResponseEntity<Product> updateproduct(@RequestBody Product product) {
		if (product != null) {
			productService.saveOrUpdateProduct(product);
			return ResponseEntity.status(HttpStatus.CREATED).body(product);
		} else {
			throw new BadRequestException("Missing Request Body");
		}
	}
}
