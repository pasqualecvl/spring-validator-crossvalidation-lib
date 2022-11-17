package it.pasqualecavallo.javax.constraint.crossvalidator;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Require com.geosys.libs.validation.javax.constraint.CrossValidated on the class
 */
@Documented
@Retention(RUNTIME)
@Target({ ElementType.FIELD })
public @interface AtLeastOneNotBlank {
	
	String[] groupsOfCrossValidation();

}
