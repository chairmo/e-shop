package com.chairmo.eshop.service;

import com.chairmo.eshop.model.CartDto;
import com.chairmo.eshop.rest.payload.ConfirmCartRequest;


public interface CartService {
	void emptyCart();
	CartDto addProductToCart(Long productId, int quantity);
	CartDto incrementCartItem(Long productId, int quantity);
	CartDto decrementCartItem(Long productId, int quantity);
	CartDto removeCartItem(Long productId);
	boolean confirmCartRequest(ConfirmCartRequest cartRequest);
	CartDto fetchCart();
}
