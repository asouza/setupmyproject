package com.setupmyproject.models.crud;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.setupmyproject.commands.crud.steps.CrudGlobalStep;
import com.setupmyproject.components.html.DynamicTableHtmlElement;
import com.setupmyproject.components.html.FormHtmlElement;
import com.setupmyproject.components.html.FormRequestMethod;
import com.setupmyproject.components.html.HtmlEngineProcessor;
import com.setupmyproject.components.html.InputTextHtmlElement;
import com.setupmyproject.components.html.SelectOneHtmlElement;
import com.setupmyproject.components.html.TableDataHtmlElement;
import com.setupmyproject.components.html.ViewElement;
import com.setupmyproject.controllers.forms.MavenSetupForm;
import com.setupmyproject.forge.ViewSaveConfiguration;
import com.setupmyproject.infra.SetupMyProjectXMLReader;

public class CrudModelTest {
	
	public static class MockConf implements ChoosenFrameworkCrudConfiguration {

		@Override
		public HtmlEngineProcessor getTableProcessor() {
			return null;
		}

		@Override
		public String getListViewTemplatePath() {
			return null;
		}

		@Override
		public ViewSaveConfiguration getViewSaveConfiguration(
				CrudModel crudModel) {
			return null;
		}

		@Override
		public HtmlEngineProcessor getFormProcessor() {
			return null;
		}

		@Override
		public String getAddFormViewTemplatePath() {
			return null;
		}

		@Override
		public List<Entry<String, Object>> getFormMetaData(CrudModel crudModel) {
			return Arrays.asList();
		}

		@Override
		public String getControllerTemplate() {
			return null;
		}

		@Override
		public String getDaoTemplate() {
			return null;
		}

		@Override
		public String getUpdateFormViewTemplatePath() {
			return null;
		}

		@Override
		public String getInputsFormIncludeViewTemplatePath() {
			return null;
		}

		@Override
		public Optional<String> getMavenDepsPath() {
			return null;
		}

		@Override
		public ViewSaveConfiguration getSharedIncludeSaveConfiguration() {
			return null;
		}

		@Override
		public Optional<String> getHeaderViewTemplatePath() {
			return null;
		}

		@Override
		public Optional<String> getFooterViewTemplatePath() {
			return null;
		}

		@Override
		public void handleViewElement(ViewElement input, CrudField f,
				CrudModel crudModel) {
			
		}

		@Override
		public Optional<ViewSaveConfiguration> getTemplateSaveConfiguration() {
			return null;
		}

		@Override
		public Optional<String> getTemplatePath() {
			return null;
		}

		@Override
		public List<CrudGlobalStep> getCrudGlobalSteps() {
			return null;
		}
		
	}

	@Test
	public void shouldGenerateDynamicTable(){
		CrudModels models = SetupMyProjectXMLReader.read("/simple-productModel.xml",
				CrudModels.class);
		CrudModel productModel = models.getList().get(0);
		
		DynamicTableHtmlElement dynamicTable = productModel.getDynamicTable();
		
		dynamicTableAsserts(dynamicTable);		
		
		
	}
	
	@Test
	public void shouldGenerateDynamicTableWithJavaTypesOnly(){
		CrudModels models = SetupMyProjectXMLReader.read("/complex-productModel.xml",
				CrudModels.class);
		CrudModel productModel = models.getList().get(0);
		
		DynamicTableHtmlElement dynamicTable = productModel.getDynamicTable();		
		
		dynamicTableAsserts(dynamicTable);		
		
		
	}
	
	@Test
	public void shouldGenerateFormForSimpleFields(){
		CrudModels models = SetupMyProjectXMLReader.read("/simple-productModel.xml",
				CrudModels.class);
		CrudModel productModel = models.getList().get(0);
		
		FormHtmlElement form = productModel.getForm(new MockConf());
		
		Assert.assertEquals("/product", form.getAction());
		Assert.assertEquals(FormRequestMethod.POST.toString(), form.getMethod());
		List<ViewElement> elements = form.innerElements();
		
		Assert.assertTrue(elements.contains(new InputTextHtmlElement("name")));
		Assert.assertTrue(elements.contains(new InputTextHtmlElement("description")));
		
		
	}
	
	@Test
	public void shouldGenerateFormForComplexFieldsWithFormInputNameDefined(){
		CrudModels models = SetupMyProjectXMLReader.read("/complex-productModel.xml",
				CrudModels.class);
		CrudModel productModel = models.getList().get(0);
		
		FormHtmlElement form = productModel.getForm(new MockConf());
		
		Assert.assertEquals("/product", form.getAction());
		Assert.assertEquals(FormRequestMethod.POST.toString(), form.getMethod());
		List<ViewElement> elements = form.innerElements();
		
		Assert.assertTrue(elements.contains(new InputTextHtmlElement("name")));
		Assert.assertTrue(elements.contains(new InputTextHtmlElement("description")));
		Assert.assertTrue(elements.contains(new SelectOneHtmlElement("author.email")));
		
		
	}
	
	@Test
	public void shouldGenerateFormForComplexFieldsWithFormInputNameUndefined(){
		CrudModels models = SetupMyProjectXMLReader.read("/complex-productModel-input-name-undefined.xml",
				CrudModels.class);
		CrudModel productModel = models.getList().get(0);
		
		FormHtmlElement form = productModel.getForm(new MockConf());
		
		Assert.assertEquals("/product", form.getAction());
		Assert.assertEquals(FormRequestMethod.POST.toString(), form.getMethod());
		List<ViewElement> elements = form.innerElements();
		
		Assert.assertTrue(elements.contains(new InputTextHtmlElement("name")));
		Assert.assertTrue(elements.contains(new InputTextHtmlElement("description")));
		Assert.assertTrue(elements.contains(new SelectOneHtmlElement("author.id")));
		
		
	}
	
	@Test
	public void shouldGenerateFormForComplexFieldsWithoutFormInputType(){
		CrudModels models = SetupMyProjectXMLReader.read("/complex-productModel-without-formInputType.xml",
				CrudModels.class);
		CrudModel productModel = models.getList().get(0);
		
		FormHtmlElement form = productModel.getForm(new MockConf());
		
		Assert.assertEquals("/product", form.getAction());
		Assert.assertEquals(FormRequestMethod.POST.toString(), form.getMethod());
		List<ViewElement> elements = form.innerElements();
		
		Assert.assertTrue(elements.contains(new InputTextHtmlElement("name")));
		Assert.assertTrue(elements.contains(new InputTextHtmlElement("description")));
		Assert.assertTrue(elements.contains(new InputTextHtmlElement("author")));
		
		
	}	
	
	@Test
	public void shouldGenerateEntityImportsFromJavaClassesOutsideJavaLang(){
		CrudModels models = SetupMyProjectXMLReader.read("/simple-productModel.xml",
				CrudModels.class);	
		CrudModel productModel = models.getList().get(0);
		
		String modelsPackage = "com.example.project.models";
		Set<String> imports = productModel.getEntityImports(modelsPackage);
		Assert.assertEquals(1, imports.size());		
		Assert.assertTrue(imports.contains("java.math.BigDecimal"));		
		
	}
	
	@Test
	public void shouldGenerateEntityImportsForWithJavaTypesForgotten(){
		CrudModels models = SetupMyProjectXMLReader.read("/complex-productModel-forgot-java-type.xml",
				CrudModels.class);	
		CrudModel productModel = models.getList().get(0);
		
		String modelsPackage = "com.example.project.models";
		Set<String> imports = productModel.getEntityImports(modelsPackage);
		Assert.assertEquals(2, imports.size());		
		Assert.assertTrue(imports.contains("java.math.BigDecimal"));		
		Assert.assertTrue(imports.contains(modelsPackage+".Author"));		
		
	}
	
	@Test
	public void shouldGenerateEntityImportsFromJavaClassesOutsidePrimitiveTypes(){
		CrudModels models = SetupMyProjectXMLReader.read("/simple-productModel-primitive-type.xml",
				CrudModels.class);	
		CrudModel productModel = models.getList().get(0);
		
		String modelsPackage = "com.example.project.models";
		Set<String> imports = productModel.getEntityImports(modelsPackage);
		Assert.assertEquals(1, imports.size());		
		Assert.assertTrue(imports.contains("java.math.BigDecimal"));		
		
	}
	
	@Test
	public void shouldGenerateControllerImports(){
		CrudModels models = SetupMyProjectXMLReader.read("/simple-productModel.xml",
				CrudModels.class);	
		CrudModel productModel = models.getList().get(0);
		
		MavenSetupForm mavenSetupForm = new MavenSetupForm();
		mavenSetupForm.setBasePackage("com.example.project");
		Set<String> imports = productModel.getControllerImports(models,mavenSetupForm);
		Assert.assertEquals(2, imports.size());		
		Assert.assertTrue(imports.contains(mavenSetupForm.packageFor("models.Product")));		
		Assert.assertTrue(imports.contains(mavenSetupForm.packageFor("daos.ProductDao")));		
		
	}
	
	@Test
	public void shouldGenerateControllerImportsWithRelatedDaos(){
		CrudModels models = SetupMyProjectXMLReader.read("/complex-productModel.xml",
				CrudModels.class);	
		CrudModel productModel = models.getList().get(0);
		
		MavenSetupForm mavenSetupForm = new MavenSetupForm();
		mavenSetupForm.setBasePackage("com.example.project");
		Set<String> imports = productModel.getControllerImports(models,mavenSetupForm);
		Assert.assertEquals(4, imports.size());		
		Assert.assertTrue(imports.contains(mavenSetupForm.packageFor("models.Product")));		
		Assert.assertTrue(imports.contains(mavenSetupForm.packageFor("daos.ProductDao")));		
		Assert.assertTrue(imports.contains(mavenSetupForm.packageFor("daos.AuthorDao")));		
		
	}
	
	@Test
	public void shouldGenerateJavaImportsFromJavaClassesOutsideJavaLangAndCustomModels(){
		CrudModels models = SetupMyProjectXMLReader.read("/complex-productModel.xml",
				CrudModels.class);	
		CrudModel productModel = models.getList().get(0);
		
		String modelsPackage = "com.example.project.models";
		Set<String> imports = productModel.getEntityImports(modelsPackage);
		Assert.assertEquals(2, imports.size());		
		Assert.assertTrue(imports.contains("java.math.BigDecimal"));		
		Assert.assertTrue(imports.contains(modelsPackage+".Author"));		
		
	}
	
	@Test
	public void shouldNotGetItselfAsRelatedCrudModel(){
		CrudModels allModels = SetupMyProjectXMLReader.read("/simple-productModel.xml",
				CrudModels.class);	
		CrudModel productModel = allModels.getList().get(0);
		
		CrudModels crudModels = productModel.getAllRelatedCrudModels(allModels);
		List<CrudModel> list = crudModels.getList();
		Assert.assertEquals(0, list.size());
		
	}	
	
	@Test
	public void shouldGetRelatedCrudModelsThatActuallyExist(){
		CrudModels allModels = SetupMyProjectXMLReader.read("/complex-productModel.xml",
				CrudModels.class);	
		CrudModel productModel = allModels.getList().get(0);
		
		CrudModels crudModels = productModel.getAllRelatedCrudModels(allModels);
		List<CrudModel> list = crudModels.getList();
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(allModels.getList().get(1), list.get(0));
		
	}
	
	@Test
	public void shouldGetRelatedCrudModelsThatExistOrNot(){
		CrudModels allModels = SetupMyProjectXMLReader.read("/more-complex-productModel.xml",
				CrudModels.class);	
		CrudModel productModel = allModels.getList().get(0);
		
		CrudModels crudModels = productModel.getAllRelatedCrudModels(allModels);
		List<CrudModel> list = crudModels.getList();
		Assert.assertEquals(2, list.size());
		Assert.assertEquals(allModels.getList().get(1), list.get(0));
		CrudModel category = new CrudModel("Category");
		Assert.assertEquals(category, list.get(1));
		
	}
	
	@Test
	public void shouldGetRelatedCrudModelsThatExistOrNot2(){
		CrudModels allModels = SetupMyProjectXMLReader.read("/more-complex-productModel.-with-only-non-exist-relatedModel.xml",
				CrudModels.class);	
		CrudModel productModel = allModels.getList().get(0);
		
		CrudModels crudModels = productModel.getAllRelatedCrudModels(allModels);
		List<CrudModel> list = crudModels.getList();
		Assert.assertEquals(1, list.size());
		CrudModel category = new CrudModel("Category");
		Assert.assertEquals(category, list.get(0));
		
	}
	
	@Test
	public void shouldNotGetNonRelatedCrudModels(){
		CrudModels allModels = SetupMyProjectXMLReader.read("/more-complex-productModel.xml",
				CrudModels.class);	
		CrudModel userModel = allModels.getList().get(2);
		
		CrudModels crudModels = userModel.getAllRelatedCrudModels(allModels);
		List<CrudModel> list = crudModels.getList();
		Assert.assertEquals(0, list.size());
		
	}	

	private void dynamicTableAsserts(DynamicTableHtmlElement dynamicTable) {
		Assert.assertEquals("list", dynamicTable.getCollectionVariable());
		Assert.assertEquals("object", dynamicTable.getVarName());
		
		List<TableDataHtmlElement> tds = dynamicTable.getHeader().getTds();
		Assert.assertEquals(4, tds.size());
		Assert.assertTrue(tds.contains(new TableDataHtmlElement("name")));
		Assert.assertTrue(tds.contains(new TableDataHtmlElement("description")));
		Assert.assertTrue(tds.contains(new TableDataHtmlElement("intField")));
		Assert.assertTrue(tds.contains(new TableDataHtmlElement("price")));
		
		List<String> valueExpressions = dynamicTable.getRowExpression().getValueExpressions();
		Assert.assertEquals(4, valueExpressions.size());
		Assert.assertTrue(valueExpressions.contains("object.name"));
		Assert.assertTrue(valueExpressions.contains("object.description"));
		Assert.assertTrue(valueExpressions.contains("object.intField"));
		Assert.assertTrue(valueExpressions.contains("object.price"));
	}
}
