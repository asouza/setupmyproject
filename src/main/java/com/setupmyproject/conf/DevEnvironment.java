package com.setupmyproject.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component("env")
public class DevEnvironment implements AccessEnvironment{

	@Autowired
	private Environment environment;
	
	public String getProperty(String key) {
		return environment.getProperty(key);
	}

	@Override
	public boolean isDev() {
		return true;
	}
	
	
}
