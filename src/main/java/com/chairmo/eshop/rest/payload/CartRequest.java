package com.chairmo.eshop.rest.payload;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;


@Data
public class CartRequest {
	@NotNull
	@Min(1)
	private Long productId;
	@NotNull
	@Min(1)
	private int quantity;
}
