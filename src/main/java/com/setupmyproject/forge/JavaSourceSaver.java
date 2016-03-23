package com.setupmyproject.forge;

import java.util.Map;
import java.util.function.Function;

import org.jboss.forge.addon.parser.java.facets.JavaSourceFacet;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.templates.TemplateFactory;
import org.jboss.forge.roaster.model.source.JavaClassSource;

public class JavaSourceSaver {

	private JavaTemplateLoader javaTemplateLoader;
	private JavaSourceFacet javaSourceFacet;
	private Function<JavaClassSource, JavaClassSource> doNothing = (source) -> {return source;};
	
	public JavaSourceSaver(TemplateFactory templateFactory,	ResourceFactory resourceFactory,Project project) {
		super();
		javaTemplateLoader = new JavaTemplateLoader(templateFactory, resourceFactory);
		javaSourceFacet = project.getFacet(JavaSourceFacet.class);
	}

	/**
	 * 
	 * @param templatePath caminho completo no buildpath para o template. Ex: /templates/springmvc/HomeController.jv 
	 * @param packageName pacote onde a classe deve ser criada
	 */
	public void save(String templatePath, String packageName) {
		save(templatePath, packageName, doNothing);
	}
	
	/**
	 * 
	 * @param templatePath caminho completo no buildpath para o template. Ex: /templates/springmvc/HomeController.jv 
	 * @param packageName pacote onde a classe deve ser criada
	 * @param beforeSaveFunction caso seja necessário fazer alguma modificação no fonte programaticamente, antes de salvar
	 */
	public void save(String templatePath, String packageName,Function<JavaClassSource, JavaClassSource> beforeSaveFunction) {
		JavaClassSource javaSource = javaTemplateLoader.load(templatePath);
		javaSource.setPackage(packageName);		
		beforeSaveFunction.apply(javaSource);
		javaSourceFacet.saveJavaSource(javaSource);		
	}	
	
	/**
	 * 
	 * @param templatePath caminho completo no buildpath para o template. Ex: /templates/springmvc/HomeController.jv 
	 * @param packageName pacote onde a classe deve ser criada
	 */
	public void save(String templatePath, String packageName,Map<?,?> templateParams) {		
		save(templatePath, packageName, templateParams, doNothing);
	}
	
	/**
	 * 
	 * @param templatePath caminho completo no buildpath para o template. Ex: /templates/springmvc/HomeController.jv 
	 * @param packageName pacote onde a classe deve ser criada
	 */
	public void saveTest(String templatePath, String packageName,Map<?,?> templateParams) {		
		saveTest(templatePath, packageName, templateParams, doNothing);
	}

	/**
	 * 
	 * @param templatePath caminho completo no buildpath para o template. Ex: /templates/springmvc/HomeController.jv 
	 * @param packageName pacote onde a classe deve ser criada
	 * @param beforeSaveFunction caso seja necessário fazer alguma modificação no fonte programaticamente, antes de salvar
	 */
	public void save(String templatePath, String packageName,Map<?,?> templateParams,Function<JavaClassSource, JavaClassSource> beforeSaveFunction) {
		JavaClassSource source = javaTemplateLoader.processTemplate(templatePath, templateParams);
		source.setPackage(packageName);		
		beforeSaveFunction.apply(source);
		javaSourceFacet.saveJavaSource(source);	
	}
	
	/**
	 * 
	 * @param templatePath caminho completo no buildpath para o template. Ex: /templates/springmvc/HomeController.jv 
	 * @param packageName pacote onde a classe deve ser criada
	 * @param beforeSaveFunction caso seja necessário fazer alguma modificação no fonte programaticamente, antes de salvar
	 */
	public void saveTest(String templatePath, String packageName,Map<?,?> templateParams,Function<JavaClassSource, JavaClassSource> beforeSaveFunction) {
		JavaClassSource source = javaTemplateLoader.processTemplate(templatePath, templateParams);
		source.setPackage(packageName);		
		beforeSaveFunction.apply(source);
		javaSourceFacet.saveTestJavaSource(source);	
	}	
}
