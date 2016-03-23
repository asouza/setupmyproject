package com.setupmyproject.models.crud;

import org.junit.Assert;
import org.junit.Test;

public class CrudFieldTest {

	@Test
	public void shouldGenerateAccessorMethods(){
		CrudField crudField = new CrudField();
		crudField.setName("description");
		crudField.setType("java.lang.String");
		
		Assert.assertEquals("getDescription", crudField.getterName());
		Assert.assertEquals("setDescription", crudField.setterName());
	}
	
	@Test
	public void shouldGenerateAttributeDeclaration(){
		CrudField crudField = new CrudField();
		crudField.setName("description");
		crudField.setType("java.lang.String");
		
		Assert.assertEquals("private String description;", crudField.getAttributeDeclaration());
		
	}
	
	@Test
	public void shouldGenerateAttributeDeclarationWithJPAAnnotationForManyToRelation(){
		CrudField crudField = new CrudField();
		crudField.setName("author");
		crudField.setType("Author");
		crudField.setJavaType(false);
		
		Assert.assertEquals("@ManyToOne private Author author;", crudField.getAttributeDeclaration());
		
	}
	
	@Test
	public void shouldGenerateAttributeDeclarationWithJPAAnnotationForManyToRelationWithoutJavaTypeSpecified(){
		CrudField crudField = new CrudField();
		crudField.setName("author");
		crudField.setType("Author");
		
		Assert.assertEquals("@ManyToOne private Author author;", crudField.getAttributeDeclaration());
		
	}	
	
	
	@Test
	public void shouldVerifyJavaTypeForComplexType(){
		CrudField crudField = new CrudField();
		crudField.setName("teste");
		crudField.setType("java.lang.String");
		
		Assert.assertTrue(crudField.isJavaType());
	}
	
	@Test
	public void shouldVerifyJavaTypeForSimpleType(){
		CrudField crudField = new CrudField();
		crudField.setName("teste");
		crudField.setType("int");
		
		Assert.assertTrue(crudField.isJavaType());
	}
	
	@Test
	public void shouldVerifyNotJavaTypeForUnspecifiedJavaType(){
		CrudField crudField = new CrudField();
		crudField.setName("otherModel");
		crudField.setType("Author");
		
		Assert.assertFalse(crudField.isJavaType());
	}
	
	@Test
	public void shouldVerifyNotJavaTypeForSpecifiedJavaType(){
		CrudField crudField = new CrudField();
		crudField.setName("otherModel");
		crudField.setType("Author");
		crudField.setJavaType(false);
		
		Assert.assertFalse(crudField.isJavaType());
	}	
	
	@Test
	public void shoudReturnFieldNamePluralized(){
		CrudField crudField = new CrudField();
		crudField.setName("otherModel");
		crudField.setType("Author");
		crudField.setJavaType(false);
		
		CrudModels crudModels = new CrudModels();
		crudModels.setUserLanguage(UserLanguage.PORTUGUESE);
		crudField.setContext(crudModels);
		
		Assert.assertEquals("otherModelList", crudField.getNamePluralized());
	}
}
