package com.chairmo.eshop.service;

import com.chairmo.eshop.model.AddressDto;

public interface AddressService {
	AddressDto getUserAddressById(Long id);
	Long updateUserAddress(AddressDto addressDto);
	void createAddress(AddressDto addressDto);
	AddressDto getUsersAddress();
}
