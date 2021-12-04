package com.chairmo.eshop.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chairmo.eshop.domain.CartItem;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
