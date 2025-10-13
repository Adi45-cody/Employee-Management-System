package com.example.demo.validation;

import java.time.LocalDate;
import java.time.Period;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AdultValidator implements ConstraintValidator<Adult, LocalDate>{

	@Override
	public boolean isValid(LocalDate dob, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		if(dob==null)return false;
		return Period.between(dob, LocalDate.now()).getYears() >=18;
		
	}

	
	
}//end of class
