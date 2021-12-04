package com.chairmo.eshop.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.chairmo.eshop.annotations.EmailValidator;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDto {
	private Long id;

    @Size(max = 255)
    @EmailValidator
    private String email;
    
    @NotNull
    @Size(max = 40)
    private String firstName;

    @Size(max = 40)
    private String middleName;

    @Size(max = 40)
    private String lastName;
    
    @Size(min = 10, max = 13)
    private String phone;
    
    private AddressDto addressDto;

    private Boolean emailVerified = false;
}
