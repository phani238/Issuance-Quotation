package com.zurich.policy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.zurich.policy.entity.Quotation;
import com.zurich.policy.exception.RemoteServiceNotAvailableException;
import com.zurich.policy.repository.IQuotationRepository;

@Service
public class QuotationService {

	@Autowired
	IQuotationRepository quotationRepo;

	@Retryable(value = { RuntimeException.class }, maxAttempts = 3, backoff = @Backoff(delay = 10000))
	public Optional<Quotation> getQuotation(long quotationId) {
		return quotationRepo.findById(quotationId);
	}

	@Retryable(value = { RuntimeException.class }, maxAttempts = 3, backoff = @Backoff(delay = 10000))
	public List<Quotation> getQuotations() {
		return quotationRepo.findAll();
	}

	@Retryable(value = { RuntimeException.class }, maxAttempts = 3, backoff = @Backoff(delay = 10000))
	public void saveOrUpdateQuotation(Quotation quotation) {
		quotation.setStatus(true);
		quotation.setStatusCode(HttpStatus.OK.getReasonPhrase());
		quotationRepo.save(quotation);
	}

	@Recover
	public String fallback() {
		throw new RemoteServiceNotAvailableException("Server is not available");
	}
}
