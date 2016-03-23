package com.setupmyproject.models.crud;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


import com.setupmyproject.components.html.DynamicTableHtmlElement;
import com.setupmyproject.components.html.FormHtmlElement;
import com.setupmyproject.components.html.FormRequestMethod;
import com.setupmyproject.components.html.RowExpression;
import com.setupmyproject.components.html.TableHeaderHtmlElement;
import com.setupmyproject.components.html.ViewElement;
import com.setupmyproject.controllers.forms.MavenSetupForm;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "model")
public class CrudModel {

	@XmlAttribute(required = true)
	private String name;
	@XmlAttribute(required = false)
	private Boolean viewController = true;

	@XmlElement(name = "field", required = true)
	private List<CrudField> fields = new ArrayList<>();
	@XmlTransient
	private CrudModels crudModels;

	/**
	 * Jax-b constructor
	 */
	public CrudModel() {
	}

	/**
	 * 
	 * @param name
	 */
	public CrudModel(String name) {
		this.name = name;
	}

	public CrudModel add(CrudField crudField) {
		fields.add(crudField);
		return this;
	}

	public String getName() {
		return name;
	}

	public String getNamePluralized() {
		return crudModels.pluralize(getName());
	}

	public List<CrudField> getFields() {
		return fields;
	}

	public DynamicTableHtmlElement getDynamicTable() {
		DynamicTableHtmlElement table = new DynamicTableHtmlElement("list",
				"object");
		TableHeaderHtmlElement header = table.header();
		RowExpression rowExpression = table.rowExpression();

		fields.stream().filter((f) -> f.isJavaType()).forEach((field) -> {
			header.td(field.getName());
			rowExpression.td("object." + field.getName());
		});

		return table;
	}

	public FormHtmlElement getForm(ChoosenFrameworkCrudConfiguration conf) {
		FormHtmlElement form = new FormHtmlElement(
				"/" + getModuleName(), FormRequestMethod.POST,
				conf.getFormMetaData(this));
		// TODO talvez passar o optional do field
		conf.handleViewElement(form, null, this);
		fields.forEach((f) -> {
			ViewElement input = f.getFormInputType().addInput(f, form);
			conf.handleViewElement(input, f, this);
		});

		return form;
	}

	public String getClassNameAsProperty() {
		return asProperty(getName());
	}

	private String asProperty(String value) {
		return value.replaceFirst(value.substring(0, 1), value.substring(0, 1)
				.toLowerCase());
	}

	public String getClassNameAsPropertyPluralized() {
		return crudModels.pluralize(getClassNameAsProperty());
	}

	/**
	 * 
	 * @return nome do modulo. Ex: product. Serve para o nome da pasta,
	 *         endereco base de um controller action based, chave do request no
	 *         plural etc.
	 */
	public String getModuleName() {
		return getClassNameAsProperty();
	}

	/**
	 * 
	 * @return nome para ser referenciado nos links da view
	 */
	public String getUserName() {
		return getClassNameAsProperty();
	}

	/**
	 * 
	 * @return endereco da tela do formulario. Ex:/products/form
	 */
	public String getNewAction() {
		return "/" + getModuleName() + "/form";
	}

	/**
	 * 
	 * @return endereco para ser usado pela listagem e adicao. Ex:/products
	 */
	public String getListAction() {
		return "/" + getModuleName();
	}

	public String getRemoveAction() {
		return "/" + getModuleName() + "/remove";
	}

	public String getEditAction() {
		return "/" + getModuleName();
	}

	public String getDaoName() {
		return name + "Dao";
	}

	public String getDaoVariableName() {
		return getClassNameAsProperty() + "Dao";
	}

	public String getControllerName() {
		return name + "Controller";
	}

	public String getControllerNameAsProperty() {
		return asProperty(getControllerName());
	}

	public String getFirstLetter() {
		return name.substring(0, 1);
	}

	/**
	 * 
	 * @param modelsPackage
	 *            pacote base do modelo. Ex: br.com.example.models
	 * @return
	 */
	public Set<String> getEntityImports(String modelsPackage) {
		return fields.stream().filter((f) -> {
			return !new JavaAlreadyImportedType().accepts(f.getType());
		}).map((f) -> {
			String fieldType = f.getType();
			if (f.isJavaType()) {
				return fieldType;
			}
			return modelsPackage + "." + fieldType;
		}).collect(Collectors.toSet());
	}

	/**
	 * 
	 * Caso exista um atributo que não tem classe mapeada no XML, é assumido que
	 * tal estrutura já existe no projeto em andamento, então também é incluido
	 * no retorno uma instância de {@link CrudModel} relativa. Ex: Foi mapeado
	 * um modelo Product com um atributo do tipo Category, que não existe no
	 * xml. Vai ser incluído no retorno uma representação de {@link CrudModel}
	 * para a Category.
	 * 
	 * 
	 * @param allModels
	 *            os models os quais você quer fazer a busca
	 * @return todos {@link CrudModel} relacionados com esse em particular.
	 */
	public CrudModels getAllRelatedCrudModels(CrudModels allModels) {
		CrudModels relatedModels = new CrudModels();
		allModels
				.getList()
				.stream()
				.filter((model) -> !sameModel(model)
						&& isThereFieldOfModel(model))
				.forEach(relatedModels::add);
		
		findNonMappedRelatedModels(allModels, relatedModels);
		return relatedModels;
	}

	private void findNonMappedRelatedModels(CrudModels allModels,
			CrudModels relatedModels) {
		this.fields.forEach((currentField) -> {
			CrudModel possibleNonMappedModel = new CrudModel(currentField
					.getType());
			possibleNonMappedModel.setContext(this.crudModels);
			if (!currentField.isJavaType()
					&& !allModels.getList().contains(possibleNonMappedModel)) {
				relatedModels.add(possibleNonMappedModel);
			}
		});
	}

	/**
	 * 
	 * @param allModels
	 *            os models os quais você quer fazer a busca
	 * @return caso tenha encontrado o CrudField, retorna.
	 */
	public Optional<CrudField> getRelatedCrudField(CrudField searchField) {
		return fields.stream().filter(currentField -> {
			return currentField.equals(searchField);
		}).findFirst();

	}

	private boolean isThereFieldOfModel(CrudModel model) {
		return fields.stream()
				.filter(field -> field.getType().equals(model.getName()))
				.findFirst().isPresent();
	}

	private boolean sameModel(CrudModel model) {
		return this.name.equals(model.getName());
	}

	public Set<String> getControllerImports(CrudModels allModels,
			MavenSetupForm mavenSetupForm) {
		HashSet<String> imports = new HashSet<>();
		imports.add(mavenSetupForm.packageFor("daos." + this.getDaoName()));
		imports.add(mavenSetupForm.packageFor("models." + this.getName()));

		CrudModels relatedCrudModels = this.getAllRelatedCrudModels(allModels);
		for (CrudModel relatedModel : relatedCrudModels) {
			imports.add(mavenSetupForm.packageFor("daos."
					+ relatedModel.getDaoName()));
			imports.add(mavenSetupForm.packageFor("models."
					+ relatedModel.getName()));
		}

		return imports;
	}

	/**
	 * 
	 * @return informa se esse modelo precisa do controller e da view
	 */
	public boolean needsViewController() {
		return viewController;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CrudModel other = (CrudModel) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CrudModel [name=" + name + ", viewController=" + viewController
				+ "]";
	}
	
	public void setContext(CrudModels crudModels) {
		//TODO fazendo, provavelmente vai poder deixar de receber crudModels como argumento em outros lugares aqui.
		this.crudModels = crudModels;
	}
	
}
