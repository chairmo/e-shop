package com.chairmo.eshop.rest.payload;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.chairmo.eshop.annotations.EmailValidator;


@Data
public class PasswordRequest {
    @NotBlank
    @EmailValidator
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String newPassword;

    @NotBlank
    @Size(min = 6, max = 20)
    private String confirmPassword;

    @NotBlank
    private String token;
}
