package com.chairmo.eshop.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
	private int quantity;
	private long cartDtoId;
	private long productDtoId;
}
