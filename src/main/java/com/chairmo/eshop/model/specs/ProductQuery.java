package com.chairmo.eshop.model.specs;

import java.util.function.Function;

import com.chairmo.eshop.domain.Product;
import com.chairmo.eshop.model.ProductDto;

public class ProductQuery implements Function<Product, ProductDto>{

	@Override
	public ProductDto apply(Product product) {
		ProductDto productDTO = new ProductDto();
		productDTO.setCost(product.getCost());
		productDTO.setDescription(product.getDescription());
		productDTO.setSold(product.getSold());
		productDTO.setName(product.getName());
		productDTO.setStock(product.getStock());
		productDTO.setImageUrl(product.getImageUrl());
		productDTO.setId(product.getId());
		return productDTO;
	}

}
