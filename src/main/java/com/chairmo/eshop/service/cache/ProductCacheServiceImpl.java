package com.chairmo.eshop.service.cache;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.chairmo.eshop.domain.Product;
import com.chairmo.eshop.repos.ProductRepository;


@Service
@CacheConfig(cacheNames = "product")
public class ProductCacheServiceImpl implements ProductCacheService {

	private final ProductRepository repository;
	
	 public ProductCacheServiceImpl(ProductRepository repository) {
		this.repository = repository;
	}
	
	@Override
	@Cacheable(key = "#url")
	public Product findByImageUrl(String url) {
		Optional<Product> optional = repository.findByImageUrl(url);
		return optional.get();
	}
	

	@Override
	@Cacheable(key = "#root.methodName", unless = "#result.size()==0")
	public List<Product> findByDateCreatedDesc(OffsetDateTime dateCreated) {
		return repository.findByDate(dateCreated);
	}

	@Override
	@Cacheable(key = "#name")
	public List<Product> findByName(String name) {	
		return repository.findByName(name);
	}

//	@Override
//	@Cacheable(key = "#root.methodName", unless = "#result.size()==0")
//	public List<Product> findTop10ByDateCreated() {
//		return repository.findTop10ByDateCreatedDesc();
//	}
//
//	@Override
//	@Cacheable(key = "#root.methodName", unless = "#result.size()==0")
//	public List<Product> findTop10ByOrderBySoldCountDesc() {
//		return repository.findTop10ByOrderBySoldCountDesc();
//	}

}
