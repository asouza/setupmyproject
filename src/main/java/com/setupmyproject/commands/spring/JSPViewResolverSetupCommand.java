package com.setupmyproject.commands.spring;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.facets.WebResourcesFacet;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.templates.TemplateFactory;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.setupmyproject.forge.GenericForgeTemplate;
import com.setupmyproject.forge.ViewCreator;
import com.setupmyproject.forge.ViewSaveConfiguration;
import com.setupmyproject.models.CommandGenerators;
import com.setupmyproject.models.CommandGeneratorsQuery;

public class JSPViewResolverSetupCommand {
	
	private static final String templateJSPIndex = "/templates/springmvc/pages/index.ftl";
	
	private Logger logger = LoggerFactory.getLogger(JSPViewResolverSetupCommand.class);
	
	//tem que avisar que tem que adicionar o view resolver no appconfiguration

	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
		
		logger.info("Configurando jsp para o Spring MVC");
		
		TemplateFactory templateFactory = addonRegistry.getServices(TemplateFactory.class)
				.get();
		ResourceFactory resourceFactory = addonRegistry.getServices(ResourceFactory.class).get();		
		
		GenericForgeTemplate formTemplate = new GenericForgeTemplate(templateFactory, resourceFactory, project);
		ViewCreator viewCreator = new ViewCreator(formTemplate, project.getFacet(WebResourcesFacet.class));
		viewCreator.create(templateJSPIndex, new ViewSaveConfiguration("WEB-INF/views/", "jsp"));
		
		SpringMVCBasicSetupCommand springSetup = commandGenerators.findProjectCommand(SpringMVCBasicSetupCommand.class);
		springSetup.addImportAppWebConfiguration("org.springframework.web.servlet.view.InternalResourceViewResolver");
		springSetup.addMethodAppWebConfiguration(beanMethod());
		
		
	}

	private String beanMethod() {
		return "@Bean public InternalResourceViewResolver internalResourceViewResolver() { \n"
				+ "InternalResourceViewResolver resolver = new InternalResourceViewResolver(); \n"
				+ "resolver.setPrefix(\"/WEB-INF/views/\"); \n"
				+ "resolver.setSuffix(\".jsp\"); \n"
				+ "return resolver; \n"
				+ "}";
	}
	

}
