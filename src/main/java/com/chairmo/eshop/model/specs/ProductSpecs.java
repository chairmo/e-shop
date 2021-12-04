package com.chairmo.eshop.model.specs;

import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;

import com.chairmo.eshop.domain.Product;


public class ProductSpecs {
	public static Specification<Product> maxPrice(Double price){
		return (root, query, cb) -> {
			if (Objects.isNull(price)) {
			 return cb.isTrue(cb.literal(true));	
			}
			return cb.lessThan(root.get("price"), price);
		};
	}
	
	public static Specification<Product> minPrice(Double price){
		return (root, query, cb) -> {
			if (Objects.isNull(price)) {
				return cb.isTrue(cb.literal(true));
			}
			return cb.greaterThan(root.get("price"), price);
		};
	}
}
