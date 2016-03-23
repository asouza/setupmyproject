package com.setupmyproject.components;

import org.junit.Assert;
import org.junit.Test;

import com.setupmyproject.controllers.forms.ConfigurationForm;
import com.setupmyproject.models.ProjectType;

public class BsonTest {

	private CustomBsonGenerator generator = new CustomBsonGenerator();

	@Test
	public void shouldGenerateAndReadTheBson() {
		ConfigurationForm configurationForm = new ConfigurationForm();
		configurationForm.setProjectType(ProjectType.SPRING);

		String bsonArray = generator.write(configurationForm);

		ConfigurationForm read = generator.read(bsonArray,
				ConfigurationForm.class);

		Assert.assertEquals(ProjectType.SPRING, read.getProjectType());

	}
}
