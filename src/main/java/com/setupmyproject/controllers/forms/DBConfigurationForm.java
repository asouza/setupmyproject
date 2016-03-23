package com.setupmyproject.controllers.forms;

import java.util.List;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.models.DBType;

public class DBConfigurationForm implements CommandGenerator{

	private DBType dbType;

	public DBType getDbType() {
		return dbType;
	}

	public void setDbType(DBType dbType) {
		this.dbType = dbType;
	}

	@Override
	public String toString() {
		return "DBConfigurationForm [dbType=" + dbType + "]";
	}

	@Override
	public List<? extends ProjectCommand> createComand() {
		return MaybeEmptyCommand.generate(dbType);
	}

	
	
}
