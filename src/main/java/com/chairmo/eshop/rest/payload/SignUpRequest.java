package com.chairmo.eshop.rest.payload;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.chairmo.eshop.annotations.EmailValidator;
import com.chairmo.eshop.annotations.PasswordValidator;


@Data
public class SignUpRequest {

    @NotBlank
    @Size(min = 3, max = 25)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 25)
    private String lastName;

    @NotBlank
    @EmailValidator
    private String email;

    @PasswordValidator
    @Size(min = 6, max = 20)
    private String password;
}
