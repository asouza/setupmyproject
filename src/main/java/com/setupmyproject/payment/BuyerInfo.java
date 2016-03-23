package com.setupmyproject.payment;

import javax.persistence.Embeddable;

@Embeddable
public class BuyerInfo {

	private String name;
	private String email;
	
	/**
	 * hibernate
	 */
	public BuyerInfo() {
	}

	public BuyerInfo(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public static BuyerInfo free() {
		return new BuyerInfo("gratuito","freemium@setupmyproject.com");
	}

}
