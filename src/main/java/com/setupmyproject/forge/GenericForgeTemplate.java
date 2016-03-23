package com.setupmyproject.forge;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.templates.Template;
import org.jboss.forge.addon.templates.TemplateFactory;
import org.jboss.forge.addon.templates.freemarker.FreemarkerTemplate;

import freemarker.template.TemplateException;

public final class GenericForgeTemplate {

	private TemplateFactory templateFactory;
	private ResourceFactory resourceFactory;
	private String projectPath;

	public GenericForgeTemplate(TemplateFactory templateFactory,
			ResourceFactory resourceFactory, Project project) {
		super();
		this.templateFactory = templateFactory;
		this.resourceFactory = resourceFactory;
		projectPath = project.getRoot().getFullyQualifiedName();
	}


	public String process(String templatePath, Map<?, ?> params)
			throws IOException, TemplateException {
		File file = new File(projectPath + "/src/main/resources" + templatePath);
		Resource templateResource = resourceFactory.create(file);

		if (!templateResource.exists()) {
			templateResource = resourceFactory.create(getClass().getResource(
					templatePath));

		}
		Template processor = templateFactory.create(templateResource,
				FreemarkerTemplate.class);
		String output = processor.process(params);
		return output;
	}
}
