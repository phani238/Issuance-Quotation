package com.zurich.policy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication
@EntityScan(basePackages = { "com.zurich.policy.entity" })
public class CustomerPolicyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerPolicyApplication.class, args);
	}

}
