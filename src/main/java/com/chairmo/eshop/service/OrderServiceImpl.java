package com.chairmo.eshop.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.chairmo.eshop.domain.Cart;
import com.chairmo.eshop.domain.Order;
import com.chairmo.eshop.domain.OrderDetails;
import com.chairmo.eshop.domain.User;
import com.chairmo.eshop.model.OrderDto;
import com.chairmo.eshop.model.mapper.OrderMapper;
import com.chairmo.eshop.repos.OrderRepository;
import com.chairmo.eshop.rest.exceptions.InvalidExceptionError;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final UserService userService;
	private final CartService cartService;
	

	public OrderServiceImpl(final OrderRepository orderRepository,
			UserService userService, CartService cartService) {
		this.orderRepository = orderRepository;
		this.userService = userService;
		this.cartService = cartService;
	}

	@Override
	public OrderDto get(final Long id) {
		return OrderMapper.ORDER_MAPPER.toDto(
				orderRepository.findById(id).orElseThrow(() ->
				new ResponseStatusException(HttpStatus.NOT_FOUND)));
	}

	@Override
	public int getAllOrderCount() {
		User user = userService.getUser();

		return orderRepository.countAllByUser(user)
				.orElseThrow(() -> new InvalidExceptionError("Failed to fetched user count."));
	}

	@Override
	public List<OrderDto> getAllOrders(int page, int size) {
		if (!isPageValid(page, size)) {
			throw new InvalidExceptionError("Page or size is invalid.");
		}
		PageRequest request = PageRequest.of(page, size);
		User user = userService.getUser();

		return OrderMapper.ORDER_MAPPER
				.toListDto(orderRepository.findOrdersByDateCreated(user, request));
	}
	
	private boolean isPageValid(int page, int size) {
		boolean status = true;
		if (Objects.isNull(page) || page < 1) {
			status =false;
		} 
		if (Objects.isNull(size) || size < 1) {
			status = false;
		}
		return status;
	}

	@SuppressWarnings("static-access")
	@Override
	public Order createOrder(OrderDto orderDto) {
		Cart cart = userService.getUser().getCart();

		if (Objects.isNull(cart) || cart.getCartItems().isEmpty()) {
			throw new InvalidExceptionError("failed to create order.");
		}
		
		Order order = new Order();
		order.setUser(userService.getUser());
		order.setStatus(orderDto.getStatus().PROCESSING);
		order.setOrderDetails(new ArrayList<OrderDetails>());

		cart.getCartItems().forEach(cartItem -> {
			cartItem.getProduct().setSold(cartItem.getProduct().getSold() + cartItem.getQuantity());
			OrderDetails details = new OrderDetails();
			details.setQuantity(cartItem.getQuantity());
			details.setProduct(cartItem.getProduct());
			details.setOrders(order);
			order.getOrderDetails().add(details);
		});

		order.setTotalPrice(cart.getTotalPrice());
		cartService.emptyCart();

		return orderRepository.save(order);
	}

	@Override
	public List<OrderDto> getAllOrdersByDatecreated(OffsetDateTime time, int page, int size) {
		if (!isPageValid(page, size)) {
			throw new InvalidExceptionError("Page or size is invalid.");
		}
		return OrderMapper.ORDER_MAPPER
				.toListDto(orderRepository.findByDateCreated(time, PageRequest.of(page, size)));
	}

}
