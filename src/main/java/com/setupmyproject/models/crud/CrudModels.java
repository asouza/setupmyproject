package com.setupmyproject.models.crud;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "models")
@XmlAccessorType(XmlAccessType.FIELD)
public class CrudModels implements Iterable<CrudModel>{

	@XmlElement(name="model",required=true)
	private List<CrudModel> list = new ArrayList<CrudModel>();
	@XmlTransient
	private UserLanguage userLanguage = UserLanguage.PORTUGUESE;
	
	public CrudModels add(CrudModel crudModel) {
		list.add(crudModel);
		return this;
	}
	
	public void setUserLanguage(UserLanguage userLanguage) {
		this.userLanguage = userLanguage;
	}
	
	/**
	 * metodo de callback do jaxb. Passo o contexto do {@link CrudModels} para todo mundo
	 * @param unmarshaller
	 * @param parent
	 */
	private void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
		list.forEach(model -> {
			model.setContext(this);
			model.getFields().forEach(field -> field.setContext(this));
		});
	}
	
	public List<CrudModel> getList() {
		return list;
	}

	@Override
	public Iterator<CrudModel> iterator() {
		return list.iterator();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public String pluralize(String text){
		return userLanguage.pluralize(text);
	}
	
	
}
