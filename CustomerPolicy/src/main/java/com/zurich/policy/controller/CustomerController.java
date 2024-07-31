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

import com.zurich.policy.entity.Customer;
import com.zurich.policy.exception.BadRequestException;
import com.zurich.policy.exception.ResourceNotFoundException;
import com.zurich.policy.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api")
@EnableRetry
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@Operation(summary = "List of Customers", description = "Listed all customers from the core system.")
	@ApiResponse(responseCode = "201", description = "Successfully listed all the customers")
	@GetMapping("/v1/customers")
	public ResponseEntity<List<Customer>> getProducts() {
		List<Customer> customers = customerService.getCustomers();
		if (!customers.isEmpty()) {
			return new ResponseEntity<>(customers, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("No Data Available");
		}
	}

	@Operation(summary = "Listed Selected Customer", description = "Listed the selected customer issued from the core system.")
	@ApiResponse(responseCode = "201", description = "Successfully listed the selected customer")
	@GetMapping("/v1/customer")
	public ResponseEntity<Customer> getCustomer(@RequestParam(required = true) String customerId) {
		if (!customerId.isEmpty() || !customerId.isBlank()) {
			Optional<Customer> policy = customerService.getCustomer(Long.parseLong(customerId));
			if (policy.isPresent()) {
				return new ResponseEntity<>(policy.get(), HttpStatus.OK);
			} else {
				throw new ResourceNotFoundException("No Record Found with CustomerId = " + customerId);
			}
		} else {
			throw new BadRequestException("Missing Request Parameter");
		}
	}

	@Operation(summary = "Saving Customer", description = "Saving customer to the core system.")
	@ApiResponse(responseCode = "201", description = "Successfully saved the customer")
	@PostMapping("/v1/customer")
	public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
		if (customer != null) {
			customerService.saveOrUpdateCustomer(customer);
			return ResponseEntity.status(HttpStatus.CREATED).body(customer);
		} else {
			throw new BadRequestException("Missing Request Body");
		}
	}

	@Operation(summary = "Updating Customer", description = "Updating customer to the core system.")
	@ApiResponse(responseCode = "201", description = "Successfully updated the customer")
	@PutMapping("/v1/customer")
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
		if (customer != null) {
			customerService.saveOrUpdateCustomer(customer);
			return ResponseEntity.status(HttpStatus.CREATED).body(customer);
		} else {
			throw new BadRequestException("Missing Request Body");
		}
	}

}
