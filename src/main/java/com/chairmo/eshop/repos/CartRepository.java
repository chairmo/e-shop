package com.chairmo.eshop.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chairmo.eshop.domain.Cart;


public interface CartRepository extends JpaRepository<Cart, Long> {
}
