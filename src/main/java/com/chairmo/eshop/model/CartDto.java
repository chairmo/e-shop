package com.chairmo.eshop.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CartDto {
	private Double price;
    private Double totalPrice;
    private Long userId;
    List<CartItemDto> cartItemDTOs;
}
