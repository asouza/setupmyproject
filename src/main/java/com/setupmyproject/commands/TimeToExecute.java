package com.setupmyproject.commands;

import java.util.concurrent.TimeUnit;

/**
 * Representa um possivel tempmo para execucao de alguma tarefa. Tentei achar
 * uma classe pronta para isso, mas não encontrei :(.
 * 
 * @author alberto
 *
 */
public class TimeToExecute {

	private final int total;
	private final TimeUnit timeUnit;

	public TimeToExecute(int total, TimeUnit timeUnit) {
		super();
		this.total = total;
		this.timeUnit = timeUnit;
	}
	
	@Override
	public String toString() {
		return total + " " +timeUnit.toString().toLowerCase();
	}
	
	public long minutes(){
		return timeUnit.convert(total, TimeUnit.MINUTES);				
	}

}
