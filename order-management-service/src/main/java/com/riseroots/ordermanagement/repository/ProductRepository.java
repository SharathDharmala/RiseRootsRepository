package com.riseroots.ordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<com.riseroots.ordermanagement.entity.Products, Long> {
}
