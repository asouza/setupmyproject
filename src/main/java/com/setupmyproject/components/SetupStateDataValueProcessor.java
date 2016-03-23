package com.setupmyproject.components;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestDataValueProcessor;

import com.setupmyproject.models.SetupState;

/**
 * Classe responsável por adicionar o hidden do setupState nos forms. Tem que usar o form do spring :).
 * @author alberto
 *
 */
public class SetupStateDataValueProcessor implements RequestDataValueProcessor{

	@Override
	public String processAction(HttpServletRequest request, String action,
			String httpMethod) {
		return action;
	}

	@Override
	public String processFormFieldValue(HttpServletRequest request,
			String name, String value, String type) {
		return value;
	}

	@Override
	public Map<String, String> getExtraHiddenFields(HttpServletRequest request) {
		Map<String, String> hiddens = new HashMap<String, String>();
		Object setupStateAttribute = request.getAttribute("setupState");
		if(setupStateAttribute != null){
			SetupState current = (SetupState) setupStateAttribute;
			hiddens.put("setupState", current.getValue());
		}
		return hiddens;
	}

	@Override
	public String processUrl(HttpServletRequest request, String url) {
		return url;
	}

}
