package com.riseroots.customermanagement.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.riseroots.customermanagement.model.Customer;
import com.riseroots.customermanagement.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	    private final CustomerRepository customerRepository;

	    public Customer saveCustomer(Customer customer) {
	        return customerRepository.save(customer);
	    }

	    public List<Customer> getAllCustomers() {
	        return customerRepository.findAll();
	    }
	}