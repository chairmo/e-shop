package com.chairmo.eshop.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chairmo.eshop.domain.Cart;
import com.chairmo.eshop.domain.CartItem;
import com.chairmo.eshop.domain.Product;
import com.chairmo.eshop.domain.User;
import com.chairmo.eshop.model.CartDto;
import com.chairmo.eshop.model.CartItemDto;
import com.chairmo.eshop.model.mapper.CartMapper;
import com.chairmo.eshop.repos.CartRepository;
import com.chairmo.eshop.repos.UserRepository;
import com.chairmo.eshop.rest.exceptions.InvalidExceptionError;
import com.chairmo.eshop.rest.payload.ConfirmCartRequest;

@Service
public class CartServiceImpl implements CartService {

	private final CartRepository cartRepository;
	private final UserRepository userRepository;
	private final UserService userService;
	private final ProductService productService;

	public CartServiceImpl(final CartRepository cartRepository, final UserRepository userRepository,
			UserService userService, ProductService productService) {
		this.cartRepository = cartRepository;
		this.userRepository = userRepository;
		this.userService = userService;
		this.productService = productService;
	}

	private Cart saveCart(Cart cart) {
		if (Objects.isNull(cart)) {
			throw new InvalidExceptionError("cart is null");
		}
		return cartRepository.save(cart);
	}

	private Cart createCart(User user) {
		Cart cart = new Cart();
		cart.setUser(user);
		return cart;
	}
	
	private Cart getCart() {
		return userService.getUser().getCart();
	}

	
	private Cart calculatePrice(Cart cart) {
		cart.setPrice(0.0f);
		cart.setTotalPrice(0.0f);

		cart.getCartItems().forEach(cartItem -> {
			cart.setPrice(cart.getPrice() + cartItem.getProduct().getCost());

			cart.setTotalPrice(cart.getPrice() + cartItem.getQuantity() * cartItem.getProduct().getCost());

		});
		cart.setTotalPrice(Float.parseFloat(String.format("%.2f", cart.getTotalPrice())));
		return cart;
	}

	@Override
	public void emptyCart() {
		User user = userService.getUser();
		user.setCart(null);
		userRepository.save(user);

	}
	
	@Override
	public CartDto addProductToCart(Long productId, int quantity) {
		User user = userService.getUser();
		Cart cart = user.getCart();

		if (!Objects.isNull(cart) && !Objects.isNull(cart.getCartItems()) && !cart.getCartItems().isEmpty()) {
			Optional<CartItem> cartItem = cart.getCartItems().stream()
					.filter(Item -> Item.getProduct().getId().equals(productId)).findFirst();
			if (cartItem.isPresent()
					&& (cartItem.get().getProduct().getStock() < cartItem.get().getQuantity() + quantity)) {
				throw new InvalidExceptionError("Product is out of stock.");
			}
			cartItem.get().setQuantity(cartItem.get().getQuantity() + quantity);
			Cart updateCart = calculatePrice(cart);
			cart = cartRepository.save(updateCart);

			return CartMapper.CART_MAPPER.toDto(cart);
		}
		if (Objects.isNull(cart)) {
			cart = createCart(user);
		}

		Product product = productService.getProductById(productId);

		if (product.getStock() < quantity) {
			throw new InvalidExceptionError("Product is out of stock.");
		}

		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setQuantity(quantity);
		cartItem.setCart(cart);

		if (Objects.isNull(cart.getCartItems())) {
			cart.setCartItems(Collections.emptyList());
		}
		cart.getCartItems().add(cartItem);
		cart = calculatePrice(cart);

		return CartMapper.CART_MAPPER.toDto(saveCart(cart));
	}

	@Override
	public CartDto incrementCartItem(Long productId, int quantity) {
		Cart cart = new Cart().getUser().getCart();

		if (Objects.isNull(cart) || Objects.isNull(cart.getCartItems()) || cart.getCartItems().isEmpty()) {
			throw new InvalidExceptionError("cart is empty.");
		}
		CartItem cartItem = cart.getCartItems().stream().filter(item -> item.getId().equals(productId)).findFirst()
				.orElseThrow(() -> new InvalidExceptionError("Product not found."));

		if (cartItem.getProduct().getStock() < cartItem.getQuantity() + quantity) {
			throw new InvalidExceptionError("Porduct is out of stock.");
		}

		cartItem.setQuantity(cartItem.getQuantity() + quantity);
		cart = calculatePrice(cart);
		return CartMapper.CART_MAPPER.toDto(cartRepository.save(cart));
	}

	@Override
	public CartDto decrementCartItem(Long productId, int quantity) {
		User user = userService.getUser();
		Cart cart = user.getCart();

		if (Objects.isNull(cart) || Objects.isNull(cart.getCartItems()) || cart.getCartItems().isEmpty()) {
			throw new InvalidExceptionError("cart is empty.");
		}
		CartItem cartItem = cart.getCartItems().stream().filter(item -> item.getId().equals(productId)).findFirst()
				.orElseThrow(() -> new InvalidExceptionError("Product not found."));

		if (cartItem.getQuantity() <= quantity) {
			List<CartItem> cartItems = cart.getCartItems();
			cartItems.remove(cartItem);

			if (cart.getCartItems().isEmpty() || Objects.isNull(cart.getCartItems())) {
				user.setCart(null);
				userService.saveUser(user);
				return null;
			}
			cart.setCartItems(cartItems);
			cart = calculatePrice(cart);
			return CartMapper.CART_MAPPER.toDto(saveCart(cart));
		}
		cartItem.setQuantity(cartItem.getQuantity() - quantity);
		cart = calculatePrice(cart);
		return CartMapper.CART_MAPPER.toDto(saveCart(cart));
	}

	@Override
	public CartDto removeCartItem(Long productId) {
		User user = userService.getUser();
		Cart cart = user.getCart();

		if (Objects.isNull(cart) || Objects.isNull(cart.getCartItems()) || cart.getCartItems().isEmpty()) {
			throw new InvalidExceptionError("cart is empty.");
		}
		
		
		CartItem cartItem = cart.getCartItems().stream()
				.filter(item -> item.getId().equals(productId))
				.findFirst()
				.orElseThrow(() -> new InvalidExceptionError("Product not found."));
		
		List<CartItem> cartItems = cart.getCartItems();
		cartItems.remove(cartItem);
		
		if (cart.getCartItems().isEmpty() || Objects.isNull(cart.getCartItems())) {
			user.setCart(null);
			userService.saveUser(user);
			return null;
		}
		cart.setCartItems(cartItems);
		cart = calculatePrice(cart);
		return CartMapper.CART_MAPPER.toDto(saveCart(cart));
	}

	@Override
	public boolean confirmCartRequest(ConfirmCartRequest cartRequest) {
		Cart cart = getCart();
		
		if (Objects.isNull(cart) || cart.getCartItems().isEmpty()) {
			return false;
		}
		List<CartItem> cartItems = new ArrayList<>(cart.getCartItems());
		List<CartItemDto> cartItemDTOs = cartRequest.getCartItemDtos();
		
		if (cartItems.size() != cartItemDTOs.size()) {
			return false;
		}
		
		for (int i = 0; i < cartItems.size(); i++) {
			if (!cartItems.get(i).getId().equals(cartItemDTOs.get(i).getCartDtoId()) 
					&& !cartItems.get(i).getProduct().getId()
					.equals(cartItemDTOs.get(i).getProductDtoId())
					&& !cartItems.get(i).getQuantity().equals(cartItemDTOs.get(i).getQuantity())) {
				return false;
			}
		}
		if (cart.getTotalPrice().equals(cartRequest.getTotalPrice())) {
			return Objects.nonNull(cart.getTotalPrice()
					.equals(cartRequest.getTotalPrice()));
		}
		
		return false;
	}

	@Override
	public CartDto fetchCart() {
		return CartMapper.CART_MAPPER.toDto(getCart());
	}

}

