package com.setupmyproject.infra.validator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PackageValidator implements ConstraintValidator<JavaPackage, String> {

	private static final Pattern VALID_JAVA_IDENTIFIER = Pattern
			.compile("\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*(\\.\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*)*");

	private static final Set<String> RESERVED_WORDS = new HashSet<>();

	static {
		Collections.addAll(RESERVED_WORDS, "abstract", "continue", "for", "new", "switch", "assert", "default", "goto",
				"package", "synchronized", "boolean", "do", "if", "private", "this", "break", "double", "implements",
				"protected", "throw", "byte", "else", "import", "public", "throws", "case", "enum", "instanceof",
				"return", "transient", "catch", "extends", "int", "short", "try", "char", "final", "interface",
				"static", "void", "class", "finally", "long", "strictfp", "volatile", "const", "float", "native",
				"super", "while");
	}

	@Override
	public void initialize(JavaPackage constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (isNotNull(value)) {
			return isValidIdentifier(value) && isNotReservedWords(value);
		}
		return true;
	}

	/**
	 * Testa se não é uma palavra reservada.
	 * 
	 * @param value
	 * @return
	 */
	private boolean isNotReservedWords(String value) {

		List<String> subpackages;

		if (value.contains(".")) {
			subpackages = Arrays.asList(value.split("\\."));
		} else {
			subpackages = Arrays.asList(value);
		}

		return Collections.disjoint(RESERVED_WORDS, subpackages);
	}

	/**
	 * Testa se é um nome válido de identificador java
	 * 
	 * @param value
	 * @return
	 */
	private boolean isValidIdentifier(String value) {
		return VALID_JAVA_IDENTIFIER.matcher(value).matches();
	}

	private boolean isNotNull(String value) {
		return value != null;
	}
}
