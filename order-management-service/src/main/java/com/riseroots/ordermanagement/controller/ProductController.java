package com.riseroots.ordermanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.riseroots.ordermanagement.entity.Products;
import com.riseroots.ordermanagement.service.ProductService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@GetMapping("/dfproducts")
    public ResponseEntity<List<Products>> getAllProducts() {
        List<Products> products = productService.getAllProducts();
    	logger.debug("products.. {}", products);
        return ResponseEntity.ok(products);  
    }
}
