package com.setupmyproject.models;

public class DatabaseInfo {

	private String driverClassName;
	private String driverUrl;

	public DatabaseInfo(String driverClassName, String driverUrl) {
		this.driverClassName = driverClassName;
		this.driverUrl = driverUrl;
	}
	
	public String getDriverClassName() {
		return driverClassName;
	}
	
	public String getDriverUrl() {
		return driverUrl;
	}

}
