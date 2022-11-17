package it.pasqualecavallo.crossvalidator.tests;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import it.pasqualecavallo.crossvalidator.tests.model.CrossValidationTestModel;
import it.pasqualecavallo.javax.constraint.crossvalidator.AtLeastOneNotBlank;
import it.pasqualecavallo.javax.constraint.crossvalidator.validator.AtLeastOneNotBlankValidator;
import it.pasqualecavallo.javax.crossvalidator.validator.CrossValidator;

public class CrossValidationTests {

    private Validator validator;

    static {
    }
	
    @Before
    public void init() {
        Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();
        validator = factory.getValidator();
        factory.close();
        AtLeastOneNotBlankValidator atLeastOneNotBlankValidator = new AtLeastOneNotBlankValidator();
        CrossValidator.register(AtLeastOneNotBlank.class, atLeastOneNotBlankValidator);
    }
    
	@Test
	public void testAllNull() {
		CrossValidationTestModel model = new CrossValidationTestModel();
		Set<ConstraintViolation<CrossValidationTestModel>> violations = validator.validate(model);
		assertEquals(1, violations.size());
	}
	
	@Test
	public void testAllEmpty() {
		CrossValidationTestModel model = new CrossValidationTestModel();
		model.setMustBeNotBlankGroup1("");
		model.setMustBeNotBlankGroup1And2("");
		model.setMustBeNotBlankGroup2("");
		Set<ConstraintViolation<CrossValidationTestModel>> violations = validator.validate(model);
		assertEquals(1, violations.size());
	}
	
	@Test
	public void testGroup1Failure() {
		CrossValidationTestModel model = new CrossValidationTestModel();
		model.setMustBeNotBlankGroup1("");
		model.setMustBeNotBlankGroup1And2("");
		model.setMustBeNotBlankGroup2("valid");
		Set<ConstraintViolation<CrossValidationTestModel>> violations = validator.validate(model);
		assertEquals(1, violations.size());
	}

	@Test
	public void testGroup2Failure() {
		CrossValidationTestModel model = new CrossValidationTestModel();
		model.setMustBeNotBlankGroup1("valid");
		model.setMustBeNotBlankGroup1And2("");
		model.setMustBeNotBlankGroup2("");
		Set<ConstraintViolation<CrossValidationTestModel>> violations = validator.validate(model);
		assertEquals(1, violations.size());
	}

	@Test
	public void testSuccess1() {
		CrossValidationTestModel model = new CrossValidationTestModel();
		model.setMustBeNotBlankGroup1("");
		model.setMustBeNotBlankGroup1And2("valid");
		model.setMustBeNotBlankGroup2("");
		Set<ConstraintViolation<CrossValidationTestModel>> violations = validator.validate(model);
		assertEquals(0, violations.size());
	}

	@Test
	public void testSuccess2() {
		CrossValidationTestModel model = new CrossValidationTestModel();
		model.setMustBeNotBlankGroup1("valid");
		model.setMustBeNotBlankGroup1And2("");
		model.setMustBeNotBlankGroup2("valid");
		Set<ConstraintViolation<CrossValidationTestModel>> violations = validator.validate(model);
		assertEquals(0, violations.size());
	}
}
