package com.chairmo.eshop.rest.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

import com.chairmo.eshop.annotations.EmailValidator;


@Data
public class LoginRequest {
    @NotBlank
    @EmailValidator
    private String email;

    @NotBlank
    private String password;
}
