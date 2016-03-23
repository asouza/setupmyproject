package com.setupmyproject.exceptions;

/**
 * Como o download talvez seja a parte mais importante, é importante que sejamos notificados sobre qual foi o setup
 * que gerou o problema. Esse é o objetivo dessa exception, mapear este tipo de problema.
 * @author alberto
 *
 */
public class DownloadException extends RuntimeException {

	private String token;

	public DownloadException(Exception exception, String token) {
		super(exception);
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1220826442902553702L;

	
	
}
