package pacoteControllerDefinidoNoJava;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

<#list currentCrudModel.getEntityImports(modelsPackage) as importLine>
import ${importLine};	
</#list>

@Entity
public class ${currentCrudModel.getName()} {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	<#list currentCrudModel.getFields() as field>
	${field.getAttributeDeclaration()}	
	</#list>

	public Integer getId(){
		return this.id;
	}
	
	public void setId(Integer id){
		this.id = id;
	}

	<#list currentCrudModel.getFields() as field>
	public ${field.getShortNameOfType()} ${field.getterName()}(){
		return this.${field.getName()};
	}

	public void ${field.setterName()}(${field.getShortNameOfType()} ${field.getName()}){
		this.${field.getName()} = ${field.getName()}; 
	}	
	</#list>
}
