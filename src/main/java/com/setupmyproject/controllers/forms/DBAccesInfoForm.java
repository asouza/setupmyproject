package com.setupmyproject.controllers.forms;

import java.util.ArrayList;
import java.util.List;

import com.setupmyproject.commands.ProjectCommand;

public class DBAccesInfoForm implements CommandGenerator {

	private String databaseName;
	private String login;
	private String password;

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public List<? extends ProjectCommand> createComand() {
		return new ArrayList<ProjectCommand>();
	}

}
