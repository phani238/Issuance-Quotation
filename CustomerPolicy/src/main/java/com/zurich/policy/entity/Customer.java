package com.zurich.policy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customerId", unique = true)
	private long customerId;

	@Column(name = "customertName")
	private String customertName;

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customertName=" + customertName + "]";
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomertName() {
		return customertName;
	}

	public void setCustomertName(String customertName) {
		this.customertName = customertName;
	}

}
