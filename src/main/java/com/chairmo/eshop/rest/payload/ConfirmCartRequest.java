package com.chairmo.eshop.rest.payload;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.chairmo.eshop.model.CartItemDto;

import lombok.Data;

@Data
public class ConfirmCartRequest {
	@NotNull
	private List<CartItemDto> cartItemDtos;
	
	@Min(0)
	private Double totalPrice;
}
