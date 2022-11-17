package it.pasqualecavallo.crossvalidator.tests.model;

import it.pasqualecavallo.javax.constraint.crossvalidator.AtLeastOneNotBlank;
import it.pasqualecavallo.javax.crossvalidator.EnableCrossValidator;

@EnableCrossValidator
public class CrossValidationTestModel {

	@AtLeastOneNotBlank(groupsOfCrossValidation = "1")
	private String mustBeNotBlankGroup1;

	@AtLeastOneNotBlank(groupsOfCrossValidation = { "1", "2" })
	private String mustBeNotBlankGroup1And2;

	@AtLeastOneNotBlank(groupsOfCrossValidation = { "2" })
	private String mustBeNotBlankGroup2;

	public String getMustBeNotBlankGroup1() {
		return mustBeNotBlankGroup1;
	}

	public void setMustBeNotBlankGroup1(String mustBeNotBlankGroup1) {
		this.mustBeNotBlankGroup1 = mustBeNotBlankGroup1;
	}

	public String getMustBeNotBlankGroup1And2() {
		return mustBeNotBlankGroup1And2;
	}

	public void setMustBeNotBlankGroup1And2(String mustBeNotBlankGroup1And2) {
		this.mustBeNotBlankGroup1And2 = mustBeNotBlankGroup1And2;
	}

	public String getMustBeNotBlankGroup2() {
		return mustBeNotBlankGroup2;
	}

	public void setMustBeNotBlankGroup2(String mustBeNotBlankGroup2) {
		this.mustBeNotBlankGroup2 = mustBeNotBlankGroup2;
	}

}
