package com.setupmyproject.models.crud;

import com.setupmyproject.components.html.FormHtmlElement;
import com.setupmyproject.components.html.ViewElement;

public enum CrudFormInputType {

	select {
		@Override
		public ViewElement addInput(CrudField f, FormHtmlElement form) {
			if(f.hasUserDefinedInputName()) {
				return form.selectOne(f.getFormInputName());
			} else {
				return form.selectOne(f.getName()+".id");
			}
		}
	},
	text,
	decimal,
	date,
	email,
	password,
	textarea {
		@Override
		public ViewElement addInput(CrudField f, FormHtmlElement form) {
			return form.textArea(f.getFormInputName());
		}
	},
	radioButton {
		@Override
		public ViewElement addInput(CrudField f, FormHtmlElement form) {
			return form.radionButton(f.getFormInputName());
		}
	},
	checkbox {
		@Override
		public ViewElement addInput(CrudField f, FormHtmlElement form) {
			return form.checkbox(f.getFormInputName());
		}
	};

	public ViewElement addInput(CrudField f, FormHtmlElement form) {
		return form.inputText(f.getFormInputName(),f.getFormInputType().name());
	}
}
