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

import com.zurich.policy.entity.PolicyIssuance;
import com.zurich.policy.exception.BadRequestException;
import com.zurich.policy.exception.ResourceNotFoundException;
import com.zurich.policy.service.IssuanceService;

@RestController
@RequestMapping("/api")
@EnableRetry
public class IssuanceController {

	@Autowired
	IssuanceService issuanceService;

	@GetMapping("/v1/issuances")
	public ResponseEntity<List<PolicyIssuance>> getIssuances() {
		List<PolicyIssuance> issuances = issuanceService.getIssuances();
		if (!issuances.isEmpty()) {
			return new ResponseEntity<>(issuances, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("No Data Available");
		}
	}

	@GetMapping("/v1/issuance")
	public ResponseEntity<PolicyIssuance> getIssuance(@RequestParam(required = true) String polIssuanceId) {
		if (!polIssuanceId.isEmpty() || !polIssuanceId.isBlank()) {
			Optional<PolicyIssuance> issuance = issuanceService.getIssuance(Long.parseLong(polIssuanceId));
			if (issuance.isPresent()) {
				return new ResponseEntity<>(issuance.get(), HttpStatus.OK);
			} else {
				throw new ResourceNotFoundException("No Record Found with QuotationId = " + polIssuanceId);
			}
		} else {
			throw new BadRequestException("Missing Request Parameter");
		}
	}

	@PostMapping("/v1/issuance")
	public ResponseEntity<PolicyIssuance> saveIssuance(@RequestBody PolicyIssuance issuance) {
		if (issuance != null) {
			issuanceService.saveOrUpdateIssuance(issuance);
			return ResponseEntity.status(HttpStatus.CREATED).body(issuance);
		} else {
			throw new BadRequestException("Missing Request Body");
		}
	}

	@PutMapping("/v1/issuance")
	public ResponseEntity<PolicyIssuance> updateIssuance(@RequestBody PolicyIssuance issuance) {
		if (issuance != null) {
			issuanceService.saveOrUpdateIssuance(issuance);
			return ResponseEntity.status(HttpStatus.CREATED).body(issuance);
		} else {
			throw new BadRequestException("Missing Request Body");
		}
	}
}
