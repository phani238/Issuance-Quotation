package com.zurich.policy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zurich.policy.entity.PolicyIssuance;

public interface IPolicyIssuanceRepository extends JpaRepository<PolicyIssuance, Long> {

}
