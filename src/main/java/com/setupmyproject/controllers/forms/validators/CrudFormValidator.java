package com.setupmyproject.controllers.forms.validators;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.setupmyproject.controllers.forms.CrudForm;
import com.setupmyproject.infra.SetupMyProjectXMLReader;
import com.setupmyproject.infra.StringReaderXmlSource;
import com.setupmyproject.models.CRUDType;
import com.setupmyproject.models.crud.CrudModels;
import com.setupmyproject.models.exceptions.InvalidXSDException;

public class CrudFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// FIXME so coloquei o setupstate aqui, pq tava dando uma exception
		// dizendo que ele estava tentando ser validado.
		// Agora o motivo, so deus sabe.
		return true;
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (!target.getClass().equals(CrudForm.class)) {
			return;
		}

		CrudForm crudForm = (CrudForm) target;
		if (crudForm.getCrudType() == CRUDType.CRUD_CUSTOM) {

			if (StringUtils.isBlank(crudForm.getCustomModelXml())) {
				errors.rejectValue("crudType",
						"crudType.crud_custom.customXmlEmpty");
			}

			if (!errors.hasFieldErrors("crudType")) {
				try {
					SetupMyProjectXMLReader.read(new StringReaderXmlSource(
							crudForm.getCustomModelXml(), "/crudModels.xsd"),
							CrudModels.class);
				} catch (InvalidXSDException e) {
					errors.rejectValue(
							"crudType",
							"crudType.crud_custom.customXmlInvalid",
							new Object[] { e.getMessage() },
							"Your xml structure is not valid. "
									+ e.getMessage());
				}
			}
		}

	}

}
