package com.setupmyproject.exceptions.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.setupmyproject.exceptions.RequestedSetupTokenAwareException;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
public class UnauthorizedStatusException extends RuntimeException implements RequestedSetupTokenAwareException{

	private String token;

	public UnauthorizedStatusException(String msg,String token) {
		super(msg);
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}

	private static final long serialVersionUID = -6351268624033408142L;

}
