package com.riseroots.customermanagement.controller;

import org.springframework.web.bind.annotation.*;

import com.riseroots.customermanagement.model.Customer;
import com.riseroots.customermanagement.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerRepository customerRepository;
    
    @PostMapping("/add")
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
        customerRepository.save(customer);  
        return ResponseEntity.ok("Customer added successfully");
    }

    @GetMapping("/list")
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> customers = customerRepository.findAll();
        logger.info("Fetching customer list: {}", customers.size());
        return ResponseEntity.ok(customers);
    }
}
