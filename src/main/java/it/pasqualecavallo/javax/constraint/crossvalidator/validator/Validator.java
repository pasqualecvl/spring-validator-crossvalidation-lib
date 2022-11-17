package it.pasqualecavallo.javax.constraint.crossvalidator.validator;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.validation.ConstraintValidatorContext;

public interface Validator<A extends Annotation> {
	
	default void initialize(A constraintAnnotation) {}

	boolean isValid(List<Object> toCrossValidateField, ConstraintValidatorContext context);

}
