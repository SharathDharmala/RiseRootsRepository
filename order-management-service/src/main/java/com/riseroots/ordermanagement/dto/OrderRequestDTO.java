package com.riseroots.ordermanagement.dto;

import java.util.List;

import com.riseroots.ordermanagement.entity.OrderedProducts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class OrderRequestDTO {
    private String orderType;
    private List<OrderedProductDTO> orderedProducts;
}
