package com.riseroots.ordermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riseroots.ordermanagement.entity.OrderedProducts;
import com.riseroots.ordermanagement.entity.Products;

public interface OrderedProductsRepository extends JpaRepository<OrderedProducts, Long> {
	List<OrderedProducts> findByOrderId(Long orderId);
}