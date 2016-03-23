package pacoteControllerDefinidoNoJava;

import javax.inject.Inject;
import javax.validation.Valid;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import javax.transaction.Transactional;

<#list currentCrudModel.getControllerImports(crudModels,mavenForm) as importLine>
import ${importLine};	
</#list>


@Controller
@Path("/${currentCrudModel.getModuleName()}")
public class ${currentCrudModel.getControllerName()} {
	
	@Inject
	private ${currentCrudModel.getDaoName()} ${currentCrudModel.getDaoVariableName()};
	<#list relatedCrudModels.getList() as relatedCrudModel>
	@Inject
	private ${relatedCrudModel.getDaoName()} ${relatedCrudModel.getDaoVariableName()};
	</#list>
	@Inject
	private Validator validator;
	@Inject
	private Result result;		

	@Get("/form")
	public void formAdd(${currentCrudModel.getName()} ${currentCrudModel.getClassNameAsProperty()}){
		result.include("${currentCrudModel.getClassNameAsProperty()}", ${currentCrudModel.getClassNameAsProperty()});
		<#if !(relatedCrudModels.isEmpty())>
		loadFormDependencies();
		</#if>
	}
	
	<#if !relatedCrudModels.isEmpty()>
	private void loadFormDependencies(){
		<#list relatedCrudModels.getList() as relatedCrudModel>
		result.include("${relatedCrudModel.getClassNameAsPropertyPluralized()}", ${relatedCrudModel.getDaoVariableName()}.all());	
		</#list>
	}
	</#if>
	
	@Post("")
	@Transactional
	public void save(@Valid ${currentCrudModel.getName()} ${currentCrudModel.getClassNameAsProperty()}){
		validator.onErrorForwardTo(${currentCrudModel.getControllerName()}.class).formAdd(${currentCrudModel.getClassNameAsProperty()});
		${currentCrudModel.getDaoVariableName()}.save(${currentCrudModel.getClassNameAsProperty()});
		result.redirectTo(${currentCrudModel.getControllerName()}.class).list(0);		
	}
	
	@Get("/{${currentCrudModel.getClassNameAsProperty()}.id}")
	public void formUpdate(${currentCrudModel.getName()} ${currentCrudModel.getClassNameAsProperty()}){
		result.include("${currentCrudModel.getClassNameAsProperty()}", ${currentCrudModel.getDaoVariableName()}.findById(${currentCrudModel.getClassNameAsProperty()}.getId()));
		<#if !relatedCrudModels.isEmpty()>
		loadFormDependencies();
		</#if>		
	}
	
	@Get("")
	public void list(Integer page){
		if(page == null){
			page = 0;
		}
		result.include("paginatedList", ${currentCrudModel.getDaoVariableName()}.paginated(page,10));
	}
	
	//just because get is easier here. Be my guest if you want to change.
	@Get("/remove/{id}")
	@Transactional
	public void remove(Integer id){
		${currentCrudModel.getName()} ${currentCrudModel.getClassNameAsProperty()} = ${currentCrudModel.getDaoVariableName()}.findById(id);
		${currentCrudModel.getDaoVariableName()}.remove(${currentCrudModel.getClassNameAsProperty()});
		result.redirectTo(${currentCrudModel.getControllerName()}.class).list(0);
	}
	
	@Post("/{id}")
	@Transactional
	public void update(Integer id,@Valid ${currentCrudModel.getName()} ${currentCrudModel.getClassNameAsProperty()}) {
		${currentCrudModel.getClassNameAsProperty()}.setId(id);		
		validator.onErrorForwardTo(${currentCrudModel.getControllerName()}.class).formUpdate(${currentCrudModel.getClassNameAsProperty()});
		
		${currentCrudModel.getDaoVariableName()}.update(${currentCrudModel.getClassNameAsProperty()});
		result.redirectTo(${currentCrudModel.getControllerName()}.class).list(0);
	}		
}
