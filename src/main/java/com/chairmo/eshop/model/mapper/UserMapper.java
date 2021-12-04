package com.chairmo.eshop.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.chairmo.eshop.domain.User;
import com.chairmo.eshop.model.UserDto;


@Mapper
public interface UserMapper extends GenericMapper<UserDto, User> {
	static final UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);
}
