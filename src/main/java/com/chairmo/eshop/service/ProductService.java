package com.chairmo.eshop.service;

import java.time.OffsetDateTime;
import java.util.List;

import com.chairmo.eshop.domain.Product;
import com.chairmo.eshop.model.ProductDto;


public interface ProductService {
	Long createProduct(ProductDto productDTO);
	Long updateProduct(Long productId, ProductDto productDTO);	
	void deleteProduct(Long productId);	
	ProductDto getProductDtoById(Long productId);	
	ProductDto findByUrl(String url);	
	List<ProductDto> getAll(Integer page, Integer size, String sort, Double minPrice, Double maxPrice);
	Product getProductById(Long productId);
//	List<ProductDto> getMostSelling();
	List<ProductDto> getNewlyAddedProducts(OffsetDateTime date);
	List<ProductDto> searchProductDisplay(String keyword, Integer page, Integer size);
}
