package com.riseroots.cronservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riseroots.cronservice.model.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAll();
    long countByMobileNumberAndCustomerName(String mobileNumber, String customerName);
}
