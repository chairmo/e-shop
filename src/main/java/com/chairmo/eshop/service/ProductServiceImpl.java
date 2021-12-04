package com.chairmo.eshop.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chairmo.eshop.domain.Product;
import com.chairmo.eshop.model.ProductDto;
import com.chairmo.eshop.model.mapper.ProductMapper;
import com.chairmo.eshop.model.specs.ProductSpecs;
import com.chairmo.eshop.repos.ProductRepository;
import com.chairmo.eshop.rest.exceptions.InvalidExceptionError;
import com.chairmo.eshop.service.cache.ProductCacheService;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCacheService productCacheService;
//    private final ProductQuery productQuery;

    public ProductServiceImpl(final ProductRepository productRepository,
            ProductCacheService productCacheService) {
        this.productRepository = productRepository;
        this.productCacheService = productCacheService;
//        this.productQuery = query;
    }

    @Override
    public ProductDto getProductDtoById(final Long id) {
        Optional<Product> product = Optional.of(getProductById(id));
        
        if (!product.isPresent()) {
			throw new InvalidExceptionError("Product not found");
		}
        return ProductMapper.PRODUCT_MAPPER.toDto(product.get());
    }

    @Override
    @Transactional
    public Long createProduct(final ProductDto productDto) {   
    	Product product = new Product();
    	product.setCost(productDto.getCost());
    	product.setDescription(productDto.getDescription());
    	product.setImageUrl(productDto.getImageUrl());
    	product.setName(productDto.getName());
    	product.setStock(productDto.getStock());
    	product.setSold(productDto.getSold());
    	
        return productRepository.save(product).getId();
    }
    

    @Override
    @Transactional
    public Long updateProduct(final Long id, final ProductDto productDto) {
         Optional<Product> productOptional = Optional.of(getProductById(id));
         if (!productOptional.isPresent()) {
			throw new InvalidExceptionError("Product not found.");
		}
         Product product = productOptional.get();
           product.setCost(productDto.getCost());
           product.setDescription(productDto.getDescription());
           product.setImageUrl(productDto.getImageUrl());
           product.setName(productDto.getName());
           product.setStock(productDto.getStock());
           product.setSold(productDto.getSold());
           
       return productRepository.save(product).getId();
    }

    @Override
    public void deleteProduct(final Long id) {
        productRepository.deleteById(id);
    }


	@Override
	public ProductDto findByUrl(String url) {
		return ProductMapper.PRODUCT_MAPPER
				.toDto(productCacheService.findByImageUrl(url));
	}

	@SuppressWarnings("null")
	@Override
	public List<ProductDto> getAll(Integer page, Integer size,
			String sort, Double minPrice, Double maxPrice) {
	PageRequest request;
	if (!Objects.isNull(sort) || !sort.isBlank()) {
		Sort sorts = sorted(sort);;
		if (Objects.isNull(sorts)) {
			throw new InvalidExceptionError("Sort parameter entered is not valid.");
		}
		request = PageRequest.of(page, size, sorts);
	} else {
		request = PageRequest.of(page, size);
	}
	
	Specification<Product> specs = 
			Objects.requireNonNull(Specification.where(ProductSpecs.maxPrice(maxPrice)))
			.and(ProductSpecs.minPrice(minPrice));
	
		List<Product> products = productRepository.findAll(specs, request).stream()
				.collect(Collectors.toList());
		
		return ProductMapper.PRODUCT_MAPPER.toListDto(products);
				
	}
	
	private Sort sorted(String sort) {
		switch (sort) {
		case "lowest":
			return Sort.by(Sort.Direction.ASC, "price");
		case "highest":
			return Sort.by(Sort.Direction.DESC, "price");

		default:
			return null;
		}
	}

	@Override
	public Product getProductById(Long productId) {
		Optional<Product> pOptional = productRepository.findById(productId);
		if (!pOptional.isPresent()) {
			throw new InvalidExceptionError("Product not found.");
		}
		return pOptional.get();
	}

	@Override
	public List<ProductDto> getNewlyAddedProducts(OffsetDateTime date) {
		List<Product> products = productRepository.findTop10ByDateCreatedDesc(date);
		if (products.isEmpty()) {
			throw new InvalidExceptionError("Newly added products not found.");
		}
		return Objects.requireNonNull(ProductMapper.PRODUCT_MAPPER.toListDto(products));
	}

	@Override
	public List<ProductDto> searchProductDisplay(String keyword, Integer page, Integer size) {
		if (Objects.isNull(page) || Objects.isNull(size)) {
			throw new InvalidExceptionError("Page and size cannot be null.");
		}
		PageRequest request = PageRequest.of(page, size);
		List<Product> products = productRepository
				.findAllByNameContainingIgnoreCase(keyword, request);
		return ProductMapper.PRODUCT_MAPPER.toListDto(products);
	}

}
