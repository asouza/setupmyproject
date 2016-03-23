package com.setupmyproject.forge;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.templates.Template;
import org.jboss.forge.addon.templates.TemplateFactory;
import org.jboss.forge.addon.templates.freemarker.FreemarkerTemplate;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;

public class JavaTemplateLoader {

	private TemplateFactory templateFactory;
	private ResourceFactory resourceFactory;

	public JavaTemplateLoader(TemplateFactory templateFactory,
			ResourceFactory resourceFactory) {
		this.templateFactory = templateFactory;
		this.resourceFactory = resourceFactory;
	}

	/**
	 * 
	 * @param templatePath Caminho para o template. Ex: /templates/springmvc/AppWebConfiguration.jv
	 * @return
	 */
	public JavaClassSource load(String templatePath) {
		return Roaster.parse(JavaClassSource.class, getClass().getResourceAsStream(templatePath));
	}
	
	public static void main(String[] args) {

		InputStream stream = JavaClassSource.class.getResourceAsStream("/templates/jsf/basic/HelloWorldBean.jv");
		JavaClassSource classSource = Roaster.parse(JavaClassSource.class, stream);
		String classformated = Roaster.format(classSource.toString());
		System.out.println(classformated);

	}

	/**
	 * 
	 * @param templatePath Caminho para o template. Ex: /templates/springmvc/AppWebConfiguration.jv
	 * @param params parametros que devem ser aplicados no template que vai gerar o código java
	 * @return
	 */	
	public JavaClassSource processTemplate(String templatePath, Map<?, ?> params) {
		try {
			Resource<URL> templateResource = resourceFactory.create(getClass()
					.getResource(templatePath));
			Template processor = templateFactory.create(templateResource,
					FreemarkerTemplate.class);
			String output = processor.process(params);
			JavaClassSource resource = Roaster.parse(JavaClassSource.class, output);
			return resource;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
