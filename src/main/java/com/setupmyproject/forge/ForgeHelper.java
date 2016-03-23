package com.setupmyproject.forge;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.jboss.forge.addon.facets.FacetFactory;
import org.jboss.forge.addon.javaee.Configurable;
import org.jboss.forge.addon.javaee.Descriptors;
import org.jboss.forge.addon.javaee.faces.FacesFacet_2_2;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFacet;
import org.jboss.forge.addon.projects.dependencies.DependencyInstaller;
import org.jboss.forge.addon.projects.facets.WebResourcesFacet;
import org.jboss.forge.addon.resource.FileResource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.templates.TemplateFactory;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.jboss.shrinkwrap.descriptor.api.facesconfig22.WebFacesConfigDescriptor;

import com.setupmyproject.controllers.forms.ServerEnvironmentAddonForm;
import com.setupmyproject.infra.FilesCopy;
import com.setupmyproject.models.CommandGeneratorsQuery;
import com.setupmyproject.models.ServerEnvironmentAddon;

public class ForgeHelper {

	private Project project;
	private AddonRegistry addonRegistry;

	public ForgeHelper(Project project, AddonRegistry addonRegistry) {
		this.project = project;
		this.addonRegistry = addonRegistry;
	}

	private FacetFactory getFacetFactory() {
		return addonRegistry.getServices(FacetFactory.class).get();
	}

	public Project getProject() {
		return project;
	}

	/**
	 * Instala um facet no projeto. Se já estiver instalado, só busca
	 * 
	 * @param facetClass
	 *            facet que queremos instalar ou recuperar
	 * @return facet instalado
	 */
	public <F extends ProjectFacet> F installFacet(Class<F> facetClass) {
		F facet = getFacetFactory().install(project, facetClass);
		if (!facet.isInstalled())
			facet.install();
		return facet;
	}

	/**
	 * Busca o arquivo de configuração de um facet. Se o arquivo ainda não tiver
	 * sido criado, cria.
	 * 
	 * @param facetClass
	 *            facet que queremos o arquivo
	 * @return Descritor do arquivo de configuração do facet.
	 */
	public <D, F extends ProjectFacet & Configurable<D>> D getDescriptorFor(
			Class<F> facetClass) {
		F facet = installFacet(facetClass);
		D config = facet.getConfig();
		facet.saveConfig(config);
		return config;
	}

	public <D, F extends ProjectFacet & Configurable<D>> F installFacetAndDescriptor(
			Class<F> facetClass) {
		F facet = installFacet(facetClass);
		getDescriptorFor(facetClass);
		return facet;
	}

	/**
	 * Pega o instalador de dependências do forge
	 * 
	 * @return instalador de dependências
	 */
	public ForceDependencyInstaller getForceDependencyInstaller() {
		DependencyInstaller dependencyInstaller = getDependencyInstaller();
		ForceDependencyInstaller installer = new ForceDependencyInstaller(
				dependencyInstaller, project);
		return installer;
	}

	private DependencyInstaller getDependencyInstaller() {
		return addonRegistry.getServices(DependencyInstaller.class).get();
	}

	/**
	 * Verifica se uma certa facet está instalada no projeto.
	 * 
	 * @param facetClasses
	 * @return true ou false
	 */
	public <F extends ProjectFacet> boolean hasFacet(Class<F> facetClass) {
		return project.hasFacet(facetClass);
	}

	/**
	 * Instancia de JavaSourceSaver para manipular classes nos templates
	 * 
	 * @return obj
	 */
	public JavaSourceSaver getJavaSourceSaver() {
		return new JavaSourceSaver(getTemplateFactory(), getResourceFactory(),
				project);
	}

	public ViewCreator getViewCreator() {
		GenericForgeTemplate formTemplate = new GenericForgeTemplate(
				getTemplateFactory(), getResourceFactory(), project);
		return new ViewCreator(formTemplate,
				project.getFacet(WebResourcesFacet.class));
	}

	private ResourceFactory getResourceFactory() {
		return addonRegistry.getServices(ResourceFactory.class).get();
	}

	private TemplateFactory getTemplateFactory() {
		return addonRegistry.getServices(TemplateFactory.class).get();
	}

	/**
	 * Copia todas as pastas de assets para o diretorio assets da sua aplicação
	 * web
	 * 
	 * @param filesBaseFolder
	 *            Ex: /template_setup/assets
	 * @param destinyWebFolder
	 *            Pasta web de destino no projeto. Ex: assets
	 */
	public void copyFilesToCurrentProjectWebFolder(String filesBaseFolder,
			String destinyWebFolder) {
		WebResourcesFacet webFacet = project.getFacet(WebResourcesFacet.class);
		try {
			Path origem = Paths.get(ForgeHelper.class.getResource(
					filesBaseFolder).toURI());
			FileResource<?> webDestinyFolder = webFacet
					.getWebResource(destinyWebFolder);

			Path destiny = Paths.get(webDestinyFolder.getFullyQualifiedName());
			Files.walkFileTree(origem, new FilesCopy(origem, destiny));
		} catch (IOException | URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	public GenericForgeTemplate getGenericForgeTemplate() {
		return new GenericForgeTemplate(getTemplateFactory(),
				getResourceFactory(), project);
	}

	/**
	 * 
	 * @param fileName
	 *            nome do arquivo que deve ser gerado na raiz do novo projeto
	 * @param content
	 *            conteudo que deve ser escrito no arquivo
	 */
	public void saveInRootDirectory(String fileName, String content) {
		File file = new File(project.getRoot().getFullyQualifiedName() + "/"
				+ fileName);
		try (PrintWriter printWriter = new PrintWriter(file)) {
			printWriter.println(content);
			printWriter.flush();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Busca pelo tipo de servidor escolhido, o default é o {@link ServerEnvironmentAddon#SERVLET_CONTAINER_3_X}
	 * @param commandGenerators
	 * @return
	 */
	public ServerEnvironmentAddon getServerEnvironment(
			CommandGeneratorsQuery commandGenerators) {

		ServerEnvironmentAddon serverEnvironmentAddon = ServerEnvironmentAddon.SERVLET_CONTAINER_3_X;
		Optional<ServerEnvironmentAddonForm> choosenServerEnv = commandGenerators
				.ifFind(ServerEnvironmentAddonForm.class);
		if (choosenServerEnv.isPresent()) {
			serverEnvironmentAddon = choosenServerEnv.get()
					.getEnvironmentAddon();
		}
		return serverEnvironmentAddon;
	}
}
