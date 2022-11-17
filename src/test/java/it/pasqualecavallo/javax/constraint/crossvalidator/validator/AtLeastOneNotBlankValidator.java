package it.pasqualecavallo.javax.constraint.crossvalidator.validator;

import java.util.List;

import javax.validation.ConstraintValidatorContext;

import it.pasqualecavallo.javax.constraint.crossvalidator.AtLeastOneNotBlank;

public class AtLeastOneNotBlankValidator implements Validator<AtLeastOneNotBlank> {

	public boolean isValid(List<Object> value, ConstraintValidatorContext context) {
		for(Object s : value) {
			if(s != null && !((String)s).isBlank()) {
				return true;
			}
		}
		return false;
	}
	
}
