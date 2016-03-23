package com.setupmyproject.infra.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;

@Constraint(validatedBy=PackageValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JavaPackage {

	String message() default "{com.setupmyproject.infra.validator.PackageValidator.message}";
    Class[] groups() default {};
    Class[] payload() default {};
}
