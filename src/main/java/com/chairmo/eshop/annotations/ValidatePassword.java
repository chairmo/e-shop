package com.chairmo.eshop.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.chairmo.eshop.rest.payload.PasswordRequest;
import com.chairmo.eshop.rest.payload.RequestPasswordReset;


public class ValidatePassword implements ConstraintValidator<PasswordValidator, Object> {

	
	@Override
	public void initialize(PasswordValidator constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value instanceof RequestPasswordReset) {
			RequestPasswordReset reset = (RequestPasswordReset) value;
			return reset.getNewPassword().matches(reset.getNewPasswordConfirm());
		} else if (value instanceof PasswordRequest) {
			PasswordRequest request = (PasswordRequest) value;
			return request.getNewPassword().matches(request.getConfirmPassword());
		}
		return false;
	}

}
