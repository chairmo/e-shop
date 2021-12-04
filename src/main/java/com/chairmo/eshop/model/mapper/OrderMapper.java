package com.chairmo.eshop.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.chairmo.eshop.domain.Order;
import com.chairmo.eshop.model.OrderDto;


@Mapper
public interface OrderMapper extends GenericMapper<OrderDto, Order> {
	static final OrderMapper ORDER_MAPPER = Mappers.getMapper(OrderMapper.class);
}
