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

import com.zurich.policy.entity.Quotation;
import com.zurich.policy.exception.BadRequestException;
import com.zurich.policy.exception.ResourceNotFoundException;
import com.zurich.policy.service.QuotationService;

@RestController
@RequestMapping("/api")
@EnableRetry
public class QuotationController {

	@Autowired
	QuotationService quotationService;

	@GetMapping("/v1/quotations")
	public ResponseEntity<List<Quotation>> getQuotations() {
		List<Quotation> quotations = quotationService.getQuotations();
		if (!quotations.isEmpty()) {
			return new ResponseEntity<>(quotations, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("No Data Available");
		}
	}

	@GetMapping("/v1/quotation")
	public ResponseEntity<Quotation> getQuotation(@RequestParam(required = true) String quotationId) {
		if (!quotationId.isEmpty() || !quotationId.isBlank()) {
			Optional<Quotation> quotation = quotationService.getQuotation(Long.parseLong(quotationId));
			if (quotation.isPresent()) {
				return new ResponseEntity<>(quotation.get(), HttpStatus.OK);
			} else {
				throw new ResourceNotFoundException("No Record Found with QuotationId = " + quotationId);
			}
		} else {
			throw new BadRequestException("Missing Request Parameter");
		}
	}

	@PostMapping("/v1/quotation")
	public ResponseEntity<Quotation> saveQuotation(@RequestBody Quotation quotation) {
		if (quotation != null) {
			quotationService.saveOrUpdateQuotation(quotation);
			return ResponseEntity.status(HttpStatus.CREATED).body(quotation);
		} else {
			throw new BadRequestException("Missing Request Body");
		}
	}

	@PutMapping("/v1/quotation")
	public ResponseEntity<Quotation> updateQuotation(@RequestBody Quotation quotation) {
		if (quotation != null) {
			quotationService.saveOrUpdateQuotation(quotation);
			return ResponseEntity.status(HttpStatus.CREATED).body(quotation);
		} else {
			throw new BadRequestException("Missing Request Body");
		}
	}

}
