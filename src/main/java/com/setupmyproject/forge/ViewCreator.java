package com.setupmyproject.forge;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.jboss.forge.addon.projects.facets.WebResourcesFacet;
import org.jboss.forge.addon.resource.FileResource;

import freemarker.template.TemplateException;

public class ViewCreator {

	private GenericForgeTemplate formTemplate;
	private WebResourcesFacet webFacet;

	public ViewCreator(GenericForgeTemplate formTemplate,
			WebResourcesFacet webFacet) {
		this.formTemplate = formTemplate;
		this.webFacet = webFacet;
	}

	/**
	 * 
	 * @param templatePath caminho para o template freemarker. Ex: /templates/springmvc/index-jsp.ftl
	 * @param viewConfiguration
	 * @throws IOException
	 * @throws TemplateException
	 */
	public void create(String templatePath,ViewSaveConfiguration viewConfiguration) {
		create(templatePath, viewConfiguration,new HashMap<Object, Object>());
	}
	
	/**
	 * 
	 * @param templatePath caminho para o template freemarker. Ex: /templates/springmvc/index-jsp.ftl
	 * @param viewConfiguration
	 * @param params parametros para serem utilizados no template
	 * @throws IOException
	 * @throws TemplateException
	 */
	public void create(String templatePath,ViewSaveConfiguration viewConfiguration,Map<Object, Object> params) {
			String fileTemplateName = FilenameUtils.getBaseName(templatePath);
			create(templatePath,viewConfiguration,fileTemplateName,params);

	}
	
	/**
	 * 
	 * @param templatePath caminho para o template freemarker. Ex: /templates/springmvc/index-jsp.ftl
	 * @param viewConfiguration
	 * @param params parametros para serem utilizados no template
	 * @param newFileName nome do arquivo que deve ser criado. Ex: index
	 * @throws IOException
	 * @throws TemplateException
	 */
	public void create(String templatePath,ViewSaveConfiguration viewConfiguration,String newFileName,Map<Object, Object> params) {
		try {
			String jspContent = formTemplate.process(templatePath, params);
			FileResource<?> webResource = webFacet.getWebResource(viewConfiguration.pathTo(newFileName));
			
			webResource.setContents(jspContent).createNewFile();
		} catch (IOException | TemplateException e) {
			throw new RuntimeException(e);
		}
		
	}	
}
