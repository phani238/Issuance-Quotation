package com.zurich.policy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zurich.policy.entity.Quotation;

@Repository
public interface IQuotationRepository extends JpaRepository<Quotation, Long> {

}
