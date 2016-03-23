package com.setupmyproject.conf;

/**
 * Interface que define os métodos disponíveis para serem acessado por ambiente. Depois basta ir adicionando os métodos que
 * devem ser expostos para o povo que usa.
 * @author alberto
 *
 */
public interface AccessEnvironment {

	String getProperty(String key);
	
	boolean isDev();
	
	default boolean isProd(){
		return this.getClass().equals(ProductionEnvironment.class);
	}
}
