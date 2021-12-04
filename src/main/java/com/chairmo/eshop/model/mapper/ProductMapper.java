package com.chairmo.eshop.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.chairmo.eshop.domain.Product;
import com.chairmo.eshop.model.ProductDto;


@Mapper
public interface ProductMapper extends GenericMapper<ProductDto, Product> {
	static final ProductMapper PRODUCT_MAPPER = Mappers.getMapper(ProductMapper.class);
}
