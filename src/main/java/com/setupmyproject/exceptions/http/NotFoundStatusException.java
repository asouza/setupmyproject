package com.setupmyproject.exceptions.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.setupmyproject.exceptions.RequestedSetupTokenAwareException;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class NotFoundStatusException extends RuntimeException implements RequestedSetupTokenAwareException {

	private String token;

	public NotFoundStatusException(String msg,String token) {
		super(msg);
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7180306583964334116L;

}
