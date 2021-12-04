package com.chairmo.eshop.domain;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;


@Embeddable
@Data
public class Name {
	@NotNull
    @Size(max = 40)
    private String firstName;

    @Size(max = 40)
    private String middleName;

    @Size(max = 40)
    private String lastName;
    
    @Size(min = 10, max = 13)
    private String phone;
}
