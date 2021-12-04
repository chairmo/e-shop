package com.chairmo.eshop.service.cache;

import java.time.OffsetDateTime;
import java.util.List;

import com.chairmo.eshop.domain.Product;


public interface ProductCacheService {
	Product findByImageUrl(String url);
	List<Product> findByName(String name);
	List<Product> findByDateCreatedDesc(OffsetDateTime dateCreated);
//	List<Product> findTop10ByDateCreated();
//	List<Product> findTop10ByOrderBySoldCountDesc();
}
