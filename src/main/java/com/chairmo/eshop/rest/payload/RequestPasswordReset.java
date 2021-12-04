package com.chairmo.eshop.rest.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.chairmo.eshop.annotations.PasswordValidator;

import lombok.Data;


@Data
@PasswordValidator
public class RequestPasswordReset {
	@NotBlank
    @Size(min = 8, max = 45)
    private String oldPassword;

    @NotBlank
    @Size(min = 6, max = 20)
    private String newPassword;

    @NotBlank
    @Size(min = 6, max = 20)
    private String newPasswordConfirm;
}
