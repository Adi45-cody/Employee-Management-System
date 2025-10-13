package com.example.demo.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AdultValidator.class)
public @interface Adult {

	String message() default "Employee must be at least 18 years old";
	Class<?>[]groups() default{};
	Class<?extends Payload>[]payload() default{};
	
	
}//end of class
