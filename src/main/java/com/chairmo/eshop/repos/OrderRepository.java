package com.chairmo.eshop.repos;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.chairmo.eshop.domain.Order;
import com.chairmo.eshop.domain.User;


public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
	List<Order> findOrdersByDateCreated(User user, Pageable pageable);
	
	List<Order> findByDateCreated(OffsetDateTime dateCreated, Pageable pageable);
	
	Optional<Integer> countAllByUser(User user);
}
