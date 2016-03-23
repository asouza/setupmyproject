package com.setupmyproject.models.exceptions;

import org.xml.sax.SAXParseException;

public class InvalidXSDException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1788203912784641119L;

	public InvalidXSDException(SAXParseException e) {
		super(e.getMessage());
	}

}
