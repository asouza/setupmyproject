package com.setupmyproject.models.crud;

/**
 * Futuramente podemos deixar os usuários escolherem a lingua de geração dos
 * cruds... Será?
 * 
 * @author alberto
 *
 */
public enum UserLanguage {
	PORTUGUESE {

		@Override
		public String pluralize(String text) {
			return text+"List";
		}
		
	};
	
	abstract public String pluralize(String text);

}
