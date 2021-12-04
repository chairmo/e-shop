package com.chairmo.eshop.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
	private Long id;
	
	@NotNull
	@Size(max = 100)
	private String houseNumber;

	@NotNull
	@Size(max = 100)
	private String street;

	@NotNull
	@Size(max = 100)
	private String city;

	@NotNull
	@Size(max = 100)
	private String state;

	@NotNull
	@Size(max = 100)
	private String country;

	@NotNull
	@Size(max = 6)
	private String zipCode;
}
