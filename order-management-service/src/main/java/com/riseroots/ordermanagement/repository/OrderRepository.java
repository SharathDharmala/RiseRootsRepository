
package com.riseroots.ordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riseroots.ordermanagement.entity.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
}
