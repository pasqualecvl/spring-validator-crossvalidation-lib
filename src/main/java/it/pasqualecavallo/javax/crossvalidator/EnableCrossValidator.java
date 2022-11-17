package it.pasqualecavallo.javax.crossvalidator;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import it.pasqualecavallo.javax.crossvalidator.validator.CrossValidator;

@Documented
@Retention(RUNTIME)
@Constraint(validatedBy = { CrossValidator.class })
@Target({ ElementType.TYPE })
public @interface EnableCrossValidator {

	String message() default "Crossvalidation failed";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
