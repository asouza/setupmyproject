package com.setupmyproject.models.crud;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

@XmlAccessorType(XmlAccessType.FIELD)
public class CrudField {

	@XmlAttribute(required=true)
	private String name;
	@XmlAttribute(required=true)
	private String type;
	@XmlAttribute(required=false)
	private Boolean javaType = true;
	@XmlAttribute
	private CrudFormInputType formInputType = CrudFormInputType.text;
	@XmlAttribute
	private String formInputName;
	@XmlAttribute
	private String selectLabelField;
	@XmlTransient
	private CrudModels crudModels;
	
	public CrudFormInputType getFormInputType() {
		return formInputType;
	}
	
	public String getFormInputName() {		
		if(StringUtils.isBlank(formInputName)){
			return name;
		}
		return formInputName;
	}
	
	public String getName() {
		return name;
	}
	
	//para testes. Ah, melhor que escrever um xml só para manter o encapsulamento. #prontofalei
	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}
	
	//veja o comentario do setName
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * @return tenta adivinhar se é um tipo java mesmo ou usuario errou.
	 */
	public boolean isJavaType() {
		return javaType && (new PackageDefinedType().accepts(type) || new PrimitiveTypes().accepts(type));
	}
	
	//veja o comentario do setName
	public void setJavaType(boolean javaType) {
		this.javaType = javaType;
	}

	public boolean hasUserDefinedInputName() {
		return StringUtils.isNotBlank(formInputName);
	}
	
	public String getterName(){
		return "get"+upperFirstLetter();
	}
	
	public String setterName(){
		return "set"+upperFirstLetter();
	}
	
	private String upperFirstLetter(){
		return name.substring(0,1).toUpperCase()+name.substring(1);
	}

	public String getAttributeDeclaration() {	
		StringBuilder declaration = new StringBuilder();
		if(!isJavaType()){
			declaration.append("@ManyToOne ");
		}
		declaration.append("private "+getShortNameOfType()+" "+name+";");
		return declaration.toString();
	}

	/**
	 * 
	 * @return retorna o nome unqualifed do tipo. Ex: java.math.BigDecimal vira BigDecimal
	 */
	public String getShortNameOfType() {
		return ClassUtils.getShortCanonicalName(type);
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
		CrudField other = (CrudField) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * 
	 * @return atributo que deve ser usado para exibir as labels nos selects da vida, relacionado a este tipo
	 */
	public String getSelectLabelField() {
		if(StringUtils.isBlank(selectLabelField)){
			return "id";
		}
		return selectLabelField;
	}

	public String getNamePluralized() {
		return crudModels.pluralize(this.name);
	}

	public void setContext(CrudModels crudModels) {
		this.crudModels = crudModels;
	}
	
	
	
	
	
}
