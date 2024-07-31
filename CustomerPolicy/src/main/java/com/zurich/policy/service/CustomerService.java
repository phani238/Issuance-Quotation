package com.zurich.policy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.zurich.policy.entity.Customer;
import com.zurich.policy.exception.RemoteServiceNotAvailableException;
import com.zurich.policy.repository.ICustomerRepository;

@Service
public class CustomerService {

	@Autowired
	ICustomerRepository customerRepo;

	@Retryable(value = { RuntimeException.class }, maxAttempts = 3, backoff = @Backoff(delay = 10000))
	public Optional<Customer> getCustomer(long customerId) {
		return customerRepo.findById(customerId);
	}

	@Retryable(value = { RuntimeException.class }, maxAttempts = 3, backoff = @Backoff(delay = 10000))
	public List<Customer> getCustomers() {
		return customerRepo.findAll();
	}

	@Retryable(value = { RuntimeException.class }, maxAttempts = 3, backoff = @Backoff(delay = 10000))
	public void saveOrUpdateCustomer(Customer customer) {
		customerRepo.save(customer);
	}

	@Recover
	public String fallback() {
		throw new RemoteServiceNotAvailableException("Server is not available");
	}
}
