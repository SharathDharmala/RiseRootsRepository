
package com.riseroots.ordermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.riseroots.ordermanagement.dto.OrderRequestDTO;
import com.riseroots.ordermanagement.entity.OrderedProducts;
import com.riseroots.ordermanagement.entity.Orders;
import com.riseroots.ordermanagement.entity.Products;
import com.riseroots.ordermanagement.repository.OrderRepository;
import com.riseroots.ordermanagement.repository.OrderedProductsRepository;
import com.riseroots.ordermanagement.repository.ProductRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Data
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderedProductsRepository orderedProductsRepository;  // Assuming you have this repo
	private final ProductRepository productRepository;
    
	public Orders createOrder(OrderRequestDTO orderDTO) {
	    System.out.println("Controller at OrderService...");

	    // Step 1: Create and save initial order (to generate orderId)
	    Orders newOrder = new Orders();
	    newOrder.setOrderType(orderDTO.getOrderType());
	    newOrder.setOrderDate(LocalDateTime.now());
	    newOrder.setOrderPriceInr(0.0);  // Default, will update later

	    Orders savedOrder = orderRepository.save(newOrder);

	    // Step 2: Create ordered products with calculated prices
	    List<OrderedProducts> orderedProducts = orderDTO.getOrderedProducts().stream()
	            .map(dto -> {
	                Products product = productRepository.findById(dto.getProductId())
	                        .orElseThrow(() -> new RuntimeException("Product not found: " + dto.getProductId()));

	                double pricePerGram = product.getPrice_per_grm_inr();
	                int quantity = dto.getQuantity();
	                double totalPrice = pricePerGram * quantity;

	                OrderedProducts op = new OrderedProducts();
	                op.setOrderId(savedOrder.getOrderId());
	                op.setProductId(dto.getProductId());
	                op.setQuantity(quantity);
	                op.setQuantity_price_inr(totalPrice);
	                op.setRemarks(dto.getRemarks());

	                return op;
	            })
	            .toList();

	    // Step 3: Save all ordered products
	    orderedProductsRepository.saveAll(orderedProducts);

	    // Step 4: Calculate total order price
	    double cumulativePrice = orderedProducts.stream()
	            .mapToDouble(OrderedProducts::getQuantity_price_inr)
	            .sum();

	    // Step 5: Update and save the order with final price
	    savedOrder.setOrderPriceInr(cumulativePrice);
	    return orderRepository.save(savedOrder);
	}



    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }
}

