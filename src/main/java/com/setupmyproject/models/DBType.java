package com.setupmyproject.models;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;

import com.setupmyproject.commands.RelationalDBCommand;
import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.components.ProjectCommandFormItem;

public enum DBType implements ProjectCommandFormItem,Tooltipable {
	
	MYSQL5("com.mysql.jdbc.Driver") {
		@Override
		public DependencyBuilder getDepedencyBuilder() {
			return DependencyBuilder.create().setGroupId("mysql")
					.setArtifactId("mysql-connector-java").setVersion("5.1.35");
		}
		
		public String url(String dbName){
			return "jdbc:mysql://localhost/"+dbName;
		}
		
	},
	ORACLE("oracle.jdbc.OracleDriver") {
		@Override
		public DependencyBuilder getDepedencyBuilder() {
			return DependencyBuilder.create().setGroupId("com.oracle")
					.setArtifactId("ojdbc14").setVersion("10.2.0.4.0");
		}
		
		public String url(String dbName){
			return "jdbc:oracle:thin:@myhost:1521:"+dbName;
		}
	},
	POSTGRESQL("org.postgresql.Driver") {
		@Override
		public DependencyBuilder getDepedencyBuilder() {
			return DependencyBuilder.create().setGroupId("org.postgresql")
					.setArtifactId("postgresql").setVersion("9.4-1201-jdbc41");
		}
		
		public String url(String dbName){
			return "jdbc:postgresql://localhost/"+dbName;
		}		
	},
	SQLSERVER("net.sourceforge.jtds.jdbc.Driver") {
		@Override
		public DependencyBuilder getDepedencyBuilder() {
			return DependencyBuilder.create().setGroupId("net.sourceforge.jtds").setArtifactId("jtds")
					.setVersion("1.3.1");
		}
		
		public String url(String dbName){
			return "jdbc:jtds:sqlserver://localhost/instance:1433/"+dbName;
		}		
	};
	
	private String driverClassName;

	private DBType(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	
	public String getDriverClassName() {
		return driverClassName;
	}

	@Override
	public void execute(Project project, AddonRegistry addonRegistry,
			CommandGeneratorsQuery commandGenerators) {
		new RelationalDBCommand(this).execute(project, addonRegistry,commandGenerators);
	}

	abstract public DependencyBuilder getDepedencyBuilder();

	@Override
	public BigDecimal getPrice() {
		return BigDecimal.ZERO;
	}
	
	@Override
	public String getNameKey() {
		return "option."+name();
	}
	
	abstract public String url(String dbName); 
	
	@Override
	public TimeToExecute getTimeToExecute() {
		return new TimeToExecute(2, TimeUnit.MINUTES);
	}
}
