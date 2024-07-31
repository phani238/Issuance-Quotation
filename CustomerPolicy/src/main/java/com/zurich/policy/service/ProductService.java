package com.zurich.policy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.zurich.policy.entity.Product;
import com.zurich.policy.exception.RemoteServiceNotAvailableException;
import com.zurich.policy.repository.IProductRepository;

@Service
public class ProductService {

	@Autowired
	IProductRepository productRepo;

	@Retryable(value = { RuntimeException.class }, maxAttempts = 3, backoff = @Backoff(delay = 10000))
	public Optional<Product> getProduct(Long productId) {
		return productRepo.findById(productId);
	}

	@Retryable(value = { RuntimeException.class }, maxAttempts = 3, backoff = @Backoff(delay = 10000))
	public List<Product> getProducts() {
		return productRepo.findAll();
	}

	@Retryable(value = { RuntimeException.class }, maxAttempts = 3, backoff = @Backoff(delay = 10000))
	public void saveOrUpdateProduct(Product product) {
		productRepo.save(product);
	}

	@Recover
	public String fallback() {
		throw new RemoteServiceNotAvailableException("Server is not available");
	}
}
