package com.chairmo.eshop.rest.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

import com.chairmo.eshop.annotations.EmailValidator;

@Data
public class VerifyEmailRequest {
    @NotBlank
    @EmailValidator
    private String email;

    private Integer otpNo;
}
