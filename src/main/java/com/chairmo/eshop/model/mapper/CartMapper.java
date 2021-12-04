package com.chairmo.eshop.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.chairmo.eshop.domain.Cart;
import com.chairmo.eshop.model.CartDto;

@Mapper
public interface CartMapper extends GenericMapper<CartDto, Cart> {
	static final CartMapper CART_MAPPER = Mappers.getMapper(CartMapper.class);
}
