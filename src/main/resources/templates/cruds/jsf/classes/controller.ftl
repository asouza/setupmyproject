package pacoteControllerDefinidoNoJava;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

<#list currentCrudModel.getControllerImports(crudModels,mavenForm) as importLine>
import ${importLine};	
</#list>


@Model
@Transactional
public class ${currentCrudModel.getControllerName()} {
	
	@Inject
	private ${currentCrudModel.getDaoName()} ${currentCrudModel.getDaoVariableName()};
	private ${currentCrudModel.getName()} ${currentCrudModel.getClassNameAsProperty()} = new ${currentCrudModel.getName()}();
	private List<${currentCrudModel.getName()}> ${currentCrudModel.getClassNameAsPropertyPluralized()} = new ArrayList<>();
	
	<#list relatedCrudModels.getList() as relatedCrudModel>
	@Inject
	private ${relatedCrudModel.getDaoName()} ${relatedCrudModel.getDaoVariableName()};
	private List<${relatedCrudModel.getName()}> ${relatedCrudModel.getClassNameAsPropertyPluralized()} = new ArrayList<>();
	</#list>		
	
	private Integer idToEdit;
	
	public Integer getIdToEdit() {
		return idToEdit;
	}

	public void setIdToEdit(Integer idToEdit) {
		this.idToEdit = idToEdit;
	}
	
	public List<${currentCrudModel.getName()}> get${currentCrudModel.getNamePluralized()}(){
		return this.${currentCrudModel.getClassNameAsPropertyPluralized()};
	}
	
	<#list relatedCrudModels.getList() as relatedCrudModel>
	public List<${relatedCrudModel.getName()}> get${relatedCrudModel.getNamePluralized()}(){
		return this.${relatedCrudModel.getClassNameAsPropertyPluralized()};
	}	
	</#list>
		
	public void set${currentCrudModel.getName()}(${currentCrudModel.getName()} ${currentCrudModel.getClassNameAsProperty()}){
		this.${currentCrudModel.getClassNameAsProperty()} = ${currentCrudModel.getClassNameAsProperty()};
	}
	
	public ${currentCrudModel.getName()} get${currentCrudModel.getName()}(){
		return this.${currentCrudModel.getClassNameAsProperty()};
	}
	
	@PostConstruct
	private void postConstruct() {
		${currentCrudModel.getClassNameAsPropertyPluralized()}.addAll(${currentCrudModel.getDaoVariableName()}.all());
		<#list relatedCrudModels.getList() as relatedCrudModel>
		${relatedCrudModel.getClassNameAsPropertyPluralized()}.addAll(${relatedCrudModel.getDaoVariableName()}.all());
		${currentCrudModel.getClassNameAsProperty()}.set${relatedCrudModel.getName()}(new ${relatedCrudModel.getName()}());
		</#list>
	}	
	
	public void loadDetails(){
		this.${currentCrudModel.getClassNameAsProperty()} = ${currentCrudModel.getDaoVariableName()}.findById(idToEdit);
	}
	
	public String save() {
		${currentCrudModel.getDaoVariableName()}.save(${currentCrudModel.getClassNameAsProperty()});
		return "${currentCrudModel.getListAction()}/list?faces-redirect=true";
	}

	public String update(Integer id) {
		${currentCrudModel.getClassNameAsProperty()}.setId(id);
		${currentCrudModel.getDaoVariableName()}.update(${currentCrudModel.getClassNameAsProperty()});
		return "${currentCrudModel.getListAction()}/list?faces-redirect=true";
	}

	public String remove(Integer id) {
		${currentCrudModel.getName()} ${currentCrudModel.getClassNameAsProperty()} = ${currentCrudModel.getDaoVariableName()}.findById(id);
		${currentCrudModel.getDaoVariableName()}.remove(${currentCrudModel.getClassNameAsProperty()});
		return "${currentCrudModel.getListAction()}/list?faces-redirect=true";
	}		
	
	

}
