package com.chairmo.eshop.repos;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.chairmo.eshop.domain.Product;


public interface ProductRepository extends PagingAndSortingRepository<Product, Long>,
JpaSpecificationExecutor<Product> {
	
	Optional<Product> findByImageUrl(String imageUrl);
	List<Product> findByName(String name);
	List<Product> findAllByNameContainingIgnoreCase(String name, PageRequest request);
	List<Product> findByDate(OffsetDateTime dateCreated);
	
	
	default List<Product> findTop10ByDateCreatedDesc(OffsetDateTime date){
		return findByDate(date, PageRequest.of(0, 10));
	}
	
	List<Product> findByDate(OffsetDateTime date, Pageable pageable);
//	List<Product> findTop10ByOrderBySoldCountDesc();
	
}
