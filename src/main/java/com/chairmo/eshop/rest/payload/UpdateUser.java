package com.chairmo.eshop.rest.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UpdateUser {
	@NotBlank
    @Size(min = 3, max = 25)
    private String firstName;
	private String middleName;
	
	@NotBlank
    @Size(min = 3, max = 25)
    private String lastName;
	
	@NotBlank
	@Size(min = 9, max = 12)
	private String phone;
    
}
