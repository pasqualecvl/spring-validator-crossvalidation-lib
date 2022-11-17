package it.pasqualecavallo.javax.crossvalidator.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import it.pasqualecavallo.javax.constraint.crossvalidator.validator.Validator;
import it.pasqualecavallo.javax.crossvalidator.EnableCrossValidator;

public class CrossValidator implements ConstraintValidator<EnableCrossValidator, Object> {

	private static Map<Class<?>, Validator<?>> registeredValidator = new HashMap<>();
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Set<String> evaluatedGroups = new TreeSet<>();
		boolean success = true;
		Class<?> type = value.getClass();
		Field[] allClassFields = type.getDeclaredFields();
		try {
			for (Field singleClassField : allClassFields) {
				Annotation[] onFieldAnnotations = singleClassField.getAnnotations();
				for (Annotation onFieldAnnotation : onFieldAnnotations) {
					if (registeredValidator.containsKey(onFieldAnnotation.annotationType())) {
						String[] groupOfCrossValidation = (String[]) onFieldAnnotation.annotationType()
								.getMethod("groupsOfCrossValidation").invoke(onFieldAnnotation);
						Object firstFieldToCrossValidate = type.getMethod("get" + 
								singleClassField.getName().substring(0, 1).toUpperCase() + singleClassField.getName().substring(1))
								.invoke(value);
						for (String singleGroupOfCrossValidation : groupOfCrossValidation) {
							List<Object> toCrossValidateField = new ArrayList<>();
							toCrossValidateField.add(firstFieldToCrossValidate);
							if (!evaluatedGroups.contains(singleGroupOfCrossValidation)) {
								for (Field lookingForSameAnnotationAndGroupsOnOtherFields : allClassFields) {
									if (!lookingForSameAnnotationAndGroupsOnOtherFields.equals(singleClassField)) {
										Annotation[] onOthersFieldAnnotations = lookingForSameAnnotationAndGroupsOnOtherFields.getAnnotations();
										for(Annotation onOthersFieldSingleAnnotation : onOthersFieldAnnotations) {
											if(onFieldAnnotation.getClass().equals(onOthersFieldSingleAnnotation.getClass())) {
												String[] onOtherGroupOfCrossValidation = (String[]) onOthersFieldSingleAnnotation.annotationType()
														.getMethod("groupsOfCrossValidation").invoke(onOthersFieldSingleAnnotation);
												for(String onOtherSingleGroupOfCrossValidation : onOtherGroupOfCrossValidation) {
													if(onOtherSingleGroupOfCrossValidation.equals(singleGroupOfCrossValidation)) {
														toCrossValidateField.add(
																type.getMethod("get" + 
																		lookingForSameAnnotationAndGroupsOnOtherFields.getName().substring(0, 1).toUpperCase() + lookingForSameAnnotationAndGroupsOnOtherFields.getName().substring(1))
																		.invoke(value));
													}
												}
											}
										}
									}
								}
							}
							if(toCrossValidateField.size() > 1) {
								boolean partialResult = registeredValidator.get(onFieldAnnotation.annotationType()).isValid(toCrossValidateField, context);
								if(!partialResult && success) {
									success = false;
								}
							}
							evaluatedGroups.add(singleGroupOfCrossValidation);
						}

					}
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return success;
	}

	public static void register(Class<?> annotation, Validator<?> validator) {
		registeredValidator.put(annotation, validator);
	}

}
