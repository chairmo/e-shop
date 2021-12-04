package com.chairmo.eshop.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Email(message = "Please input a valid email address.")
@Pattern(regexp = ".+@.+\\..+", message = "Please input a valid email address.")
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface EmailValidator {
	String message() default "Please input a valid email address.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {}; 
}
