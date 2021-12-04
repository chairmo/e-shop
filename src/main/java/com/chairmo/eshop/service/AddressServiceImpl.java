
package com.chairmo.eshop.service;

import com.chairmo.eshop.domain.Address;
import com.chairmo.eshop.domain.User;
import com.chairmo.eshop.model.AddressDto;
import com.chairmo.eshop.model.mapper.AddressMapper;
import com.chairmo.eshop.repos.AddressRepository;
import com.chairmo.eshop.rest.exceptions.InvalidExceptionError;
import java.util.Objects;

/**
 *
 * @author Chairmo
 */
public class AddressServiceImpl implements AddressService {
	private final AddressRepository addressRepository;
	private final UserService userService;

	public AddressServiceImpl(final AddressRepository addressRepository, UserService userService) {
		this.addressRepository = addressRepository;
		this.userService = userService;
	}

	@Override
	public AddressDto getUserAddressById(Long id) {
		return AddressMapper.ADDRESS_MAPPER.toDto(addressRepository.findById(id).get());
	}

	@Override
	public Long updateUserAddress(AddressDto addressDto) {
		User user = userService.getUser();

		if (!Objects.isNull(user) && user.getAddress() != null) {
			user.setAddress(saveAddress(addressDto));
			return userService.saveUser(user).getAddress().getId();
		} else {
			throw new InvalidExceptionError("failed to update address. Please login!");
		}
	}

	@Override
	public void createAddress(AddressDto addressDto) {
		User user = userService.getUser();

		if (!Objects.isNull(user)) {
			user.setAddress(saveAddress(addressDto));
			 userService.saveUser(user);
		} else {
			throw new InvalidExceptionError("failed to add address.");
		}

	}

	private Address saveAddress(AddressDto addressDto) {
		Address address = new Address();
		address.setCity(addressDto.getCity());
		address.setCountry(addressDto.getCountry());
		address.setHouseNumber(addressDto.getHouseNumber());
		address.setZipCode(addressDto.getZipCode());
		address.setState(addressDto.getState());
		address.setStreet(addressDto.getStreet());
		
		return addressRepository.save(address);
	}

	@Override
	public AddressDto getUsersAddress() {
		Address address = userService.getUser().getAddress();
		if (!Objects.isNull(address) && address.getId() != null) {
			throw new InvalidExceptionError("Address not found for user.");
		}
		return AddressMapper.ADDRESS_MAPPER.toDto(address);
	}
}
