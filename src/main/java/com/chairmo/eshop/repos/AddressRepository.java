package com.chairmo.eshop.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chairmo.eshop.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
