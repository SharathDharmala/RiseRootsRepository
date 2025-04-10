
package com.riseroots.ordermanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.riseroots.ordermanagement.dto.OrderRequestDTO;
import com.riseroots.ordermanagement.entity.Orders;
import com.riseroots.ordermanagement.service.OrderService;

import lombok.Data;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderService orderService;
    static final Logger logger= LoggerFactory.getLogger(OrderController.class);
    
    @PostMapping("/dfsubmit")
    public ResponseEntity<Orders> createOrder(@RequestBody OrderRequestDTO orderRequest) {
    	
    	System.out.println("At OrderService.. "+orderRequest);
    	
        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }

    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}
