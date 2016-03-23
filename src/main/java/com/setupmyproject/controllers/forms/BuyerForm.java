package com.setupmyproject.controllers.forms;

import org.apache.commons.lang3.StringUtils;

import com.setupmyproject.payment.BuyerInfo;


public class BuyerForm {

	private BuyerInfo user = new BuyerInfo();

	public BuyerInfo getUser() {
		return user;
	}

	public void setUser(BuyerInfo user) {
		this.user = user;
	}
	
	public boolean isValid(){
		return StringUtils.isNotBlank(user.getEmail());
	}
	
	public BuyerInfo buildBuyer(){
		BuyerInfo finalInfo = BuyerInfo.free();
		
		if(isValid()){
			finalInfo.setEmail(user.getEmail());
			finalInfo.setName(user.getEmail());
		}
		return finalInfo;
	}

	

	
}
