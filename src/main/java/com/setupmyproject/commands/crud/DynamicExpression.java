package com.setupmyproject.commands.crud;

/**
 * Classe besta, só para começar as expression languages sem ter que usar o escape do freemarker.
 * @author alberto
 *
 */
public class DynamicExpression {

	private String dynamicChar;

	/**
	 * 
	 * @param dynamicChar caracter que marca o inicio. Ex: Para JSF é #, para expression language normal é $ 
	 */
	public DynamicExpression(String dynamicChar) {
		this.dynamicChar = dynamicChar;
	}
	
	public String begin(){
		return dynamicChar+"{";
	}
	
	public String write(String expression){
		return dynamicChar+"{"+expression+"}";
	}

}
