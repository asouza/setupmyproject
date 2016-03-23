package com.setupmyproject.controllers.forms;

import org.junit.Assert;
import org.junit.Test;

import com.setupmyproject.models.MavenDependency;

public class MavenSetupFormTest {

	@Test
	public void shouldInferMavenInformation(){
		MavenSetupForm mavenSetupForm = new MavenSetupForm();
		mavenSetupForm.setBasePackage("br.com.ideiasaleatorias.setupmyproject");
		MavenDependency dependency = mavenSetupForm.getMavenInfo();
		Assert.assertEquals("br.com.ideiasaleatorias", dependency.getGroupId());
		Assert.assertEquals("setupmyproject", dependency.getArtifactId());
		Assert.assertEquals("br.com.ideiasaleatorias.setupmyproject", mavenSetupForm.getBasePackage());
		Assert.assertEquals("1.0.0-SNAPSHOT", dependency.getVersion());
	}
	
	@Test
	public void shouldInferMavenInformation2(){
		MavenSetupForm mavenSetupForm = new MavenSetupForm();
		mavenSetupForm.setBasePackage("br.setupmyproject");
		MavenDependency dependency = mavenSetupForm.getMavenInfo();
		Assert.assertEquals("br", dependency.getGroupId());
		Assert.assertEquals("setupmyproject", dependency.getArtifactId());
		Assert.assertEquals("br.setupmyproject", mavenSetupForm.getBasePackage());
		Assert.assertEquals("1.0.0-SNAPSHOT", dependency.getVersion());
	}
	
	@Test
	public void shouldInferMavenInformationForStrangeBasePackage(){
		MavenSetupForm mavenSetupForm = new MavenSetupForm();
		mavenSetupForm.setBasePackage("setupmyproject");
		MavenDependency dependency = mavenSetupForm.getMavenInfo();
		Assert.assertEquals("setupmyproject", dependency.getGroupId());
		Assert.assertEquals("setupmyproject", dependency.getArtifactId());
		Assert.assertEquals("setupmyproject", mavenSetupForm.getBasePackage());
		Assert.assertEquals("1.0.0-SNAPSHOT", dependency.getVersion());
	}
}
