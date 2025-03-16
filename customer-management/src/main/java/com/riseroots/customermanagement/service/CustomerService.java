package com.riseroots.customermanagement.service;

import java.util.List;

import com.riseroots.customermanagement.model.Customer;

public interface CustomerService {
	Customer saveCustomer(Customer customer);
    List<Customer> getAllCustomers();
}