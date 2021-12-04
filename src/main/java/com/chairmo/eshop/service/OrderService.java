package com.chairmo.eshop.service;

import java.time.OffsetDateTime;
import java.util.List;

import com.chairmo.eshop.domain.Order;
import com.chairmo.eshop.model.OrderDto;


public interface OrderService {
	OrderDto get(Long id);
	int getAllOrderCount();	
	List<OrderDto> getAllOrders(int page, int size);
	Order createOrder(OrderDto orderDto);	
	List<OrderDto> getAllOrdersByDatecreated(OffsetDateTime time, int page, int size);
}

