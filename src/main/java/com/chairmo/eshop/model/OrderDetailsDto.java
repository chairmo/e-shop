package com.chairmo.eshop.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsDto {
	private int quantity;
	private Long productDtoId;
	private Long orderDtoId;
}
