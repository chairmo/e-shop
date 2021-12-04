package com.chairmo.eshop.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.chairmo.eshop.domain.Address;
import com.chairmo.eshop.model.AddressDto;


@Mapper
public interface AddressMapper extends GenericMapper<AddressDto, Address>{
	static final AddressMapper ADDRESS_MAPPER = Mappers.getMapper(AddressMapper.class);

}
