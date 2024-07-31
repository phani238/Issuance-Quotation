package com.zurich.policy.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.zurich.policy.entity.PolicyIssuance;
import com.zurich.policy.exception.RemoteServiceNotAvailableException;
import com.zurich.policy.repository.IPolicyIssuanceRepository;

@Service
public class IssuanceService {

	@Autowired
	IPolicyIssuanceRepository issuanceRepo;

	@Retryable(value = { RuntimeException.class }, maxAttempts = 3, backoff = @Backoff(delay = 10000))
	public Optional<PolicyIssuance> getIssuance(long polIssuanceId) {
		return issuanceRepo.findById(polIssuanceId);
	}

	@Retryable(value = { RuntimeException.class }, maxAttempts = 3, backoff = @Backoff(delay = 10000))
	public List<PolicyIssuance> getIssuances() {
		return issuanceRepo.findAll();
	}

	@Retryable(value = { RuntimeException.class }, maxAttempts = 3, backoff = @Backoff(delay = 10000))
	public void saveOrUpdateIssuance(PolicyIssuance policyIssuance) {
		try {
			// process delays for 2 minutes
			TimeUnit.SECONDS.sleep(120);
			policyIssuance.setStatus(true);
			policyIssuance.setStatusCode(HttpStatus.OK.getReasonPhrase());
			issuanceRepo.save(policyIssuance);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
	}

	@Recover
	public String fallback() {
		throw new RemoteServiceNotAvailableException("Server is not available");
	}
}
