package com.zurich.policy.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "policyIssuance")
public class PolicyIssuance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "polIssuanceId", unique = true)
	private long polIssuanceId;

	@Column(name = "startDate")
	private Date startDate;

	@Column(name = "endDate")
	private Date endDate;

	@Column(name = "finalDiscount")
	private double finalDiscount;

	@Column(name = "finalPrice")
	private double finalPrice;

	@Column(name = "status", nullable = true, columnDefinition = "BOOLEAN default 0")
	private boolean status;

	@Column(name = "statusCode", nullable = true)
	private String statusCode;

	@ManyToOne
	@JoinColumn(name = "productId", nullable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "customerId", nullable = false)
	private Customer customer;

	@Override
	public String toString() {
		return "PolicyIssuance [polIssuanceId=" + polIssuanceId + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", finalDiscount=" + finalDiscount + ", finalPrice=" + finalPrice + ", status=" + status
				+ ", statusCode=" + statusCode + ", product=" + product + ", customer=" + customer + "]";
	}

	public long getPolIssuanceId() {
		return polIssuanceId;
	}

	public void setPolIssuanceId(long polIssuanceId) {
		this.polIssuanceId = polIssuanceId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public double getFinalDiscount() {
		return finalDiscount;
	}

	public void setFinalDiscount(double finalDiscount) {
		this.finalDiscount = finalDiscount;
	}

	public double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
