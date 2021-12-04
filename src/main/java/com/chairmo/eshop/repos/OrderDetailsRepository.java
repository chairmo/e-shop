package com.chairmo.eshop.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chairmo.eshop.domain.OrderDetails;


public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
}
