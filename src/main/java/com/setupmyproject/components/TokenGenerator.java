package com.setupmyproject.components;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {

	public String generate(){
		return UUID.randomUUID().toString();
	}
}
