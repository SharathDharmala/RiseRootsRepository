package com.riseroots.ordermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riseroots.ordermanagement.entity.Products;
import com.riseroots.ordermanagement.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collector;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Products createOrder(Products products) {
        return productRepository.save(products);
    }

    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }
}