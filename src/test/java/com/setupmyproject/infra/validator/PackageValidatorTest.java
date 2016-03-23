package com.setupmyproject.infra.validator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PackageValidatorTest {

	private PackageValidator validator;

	@Before
	public void setup() {
		validator = new PackageValidator();
	}

	@Test
	public void shouldAcceptValidPackages() {
		assertTrue(validator.isValid("br.com.empresa.teste", null));
		assertTrue(validator.isValid("com.oracle.java", null));
		assertTrue(validator.isValid("java.util.collections", null));
		assertTrue(validator.isValid("a.b.c.d", null));
		assertTrue(validator.isValid("a1.b2.b3.b4", null));
		assertTrue(validator.isValid("$strange._but.VALID", null));
		assertTrue(validator.isValid("bla", null));
		assertTrue(validator.isValid("thispublic.thatprivate", null));

	}

	@Test
	public void shouldAcceptIfPackageIsNull() {
		assertTrue(validator.isValid(null, null));
	}

	@Test
	public void shoudNotAcceptInvalidPackages() {
		assertFalse(validator.isValid("1.2.3.4", null));
		assertFalse(validator.isValid("", null));
		assertFalse(validator.isValid("and.here.w+e.go", null));
		assertFalse(validator.isValid(".why.not", null));
		assertFalse(validator.isValid("cant.this.be.", null));
		assertFalse(validator.isValid("the last.one", null));
	}

	@Test
	public void shouldNotAcceptReservedWordOnPackage() {
		assertFalse(validator.isValid("private", null));
		assertFalse(validator.isValid("this.private", null));
		assertFalse(validator.isValid("public", null));
		assertFalse(validator.isValid("package.class", null));
	}

}
