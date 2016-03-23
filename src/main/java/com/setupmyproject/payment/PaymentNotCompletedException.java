package com.setupmyproject.payment;


public class PaymentNotCompletedException extends RuntimeException {

	public PaymentNotCompletedException(String msg) {
		super(msg);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -642750931279410391L;

}
