package com.setupmyproject.components.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.servlet.tags.form.RadioButtonTag;

public class FormHtmlElement extends GenericInputFormElement {

	private String action;
	private FormRequestMethod method;
	private List<ViewElement> formElements = new ArrayList<>();
	private Map<String, Object> metaData = new HashMap<String, Object>();

	public FormHtmlElement(String action, FormRequestMethod method) {
		this.action = action;
		this.method = method;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public FormHtmlElement(String action, FormRequestMethod method,List<Entry<String, Object>> extraParams) {
		this.action = action;
		this.method = method;
		extraParams.forEach((e) -> {
			metaData.put(e.getKey(), e.getValue());
		});
	}
	
	public InputTextHtmlElement inputText(String name) {
		InputTextHtmlElement input = new InputTextHtmlElement(name);
		formElements.add(input);
		return input;
	}
	
	public InputTextHtmlElement inputText(String name,String inputType) {
		InputTextHtmlElement input = new InputTextHtmlElement(name,inputType);
		formElements.add(input);
		return input;
	}

	public TextAreaHtmlElement textArea(String name) {
		TextAreaHtmlElement textArea = new TextAreaHtmlElement(name);
		formElements.add(textArea);
		return textArea;
	}

	public SelectOneHtmlElement selectOne(String name) {
		SelectOneHtmlElement select = new SelectOneHtmlElement(name);
		formElements.add(select);
		return select;
	}
	
	@SuppressWarnings("unchecked")
	public SelectOneHtmlElement selectOne(String name,Entry<String, Object>...extraAttributes) {
		SelectOneHtmlElement select = new SelectOneHtmlElement(name,extraAttributes);
		formElements.add(new SelectOneHtmlElement(name,extraAttributes));
		return select;
	}
	
	/**
	 * 
	 * @param value do submit
	 * @return
	 */
	public SubmitHtmlElement submit(String value) {
		SubmitHtmlElement submit = new SubmitHtmlElement(value);
		formElements.add(submit);
		return submit;
	}

	@Override
	public String getName() {
		return "modelForm";
	}

	public String getAction() {
		return this.action;
	}

	public String getMethod() {
		return method.name();
	}

	@Override
	public Map<String,Object> metaData() {
		return metaData;
	}
	
	@Override
	public List<ViewElement> innerElements() {
		return formElements;
	}

	public RadioButtonHtmlElement radionButton(String formInputName) {
		RadioButtonHtmlElement radioButtonHtmlElement = new RadioButtonHtmlElement(formInputName);
		formElements.add(radioButtonHtmlElement);
		return radioButtonHtmlElement;
	}

	public CheckBoxHtmlElement checkbox(String formInputName) {
		CheckBoxHtmlElement checkBoxHtmlElement = new CheckBoxHtmlElement(formInputName);
		formElements.add(checkBoxHtmlElement);
		return checkBoxHtmlElement;
	}
	

}
