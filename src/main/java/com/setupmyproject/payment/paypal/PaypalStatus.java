package com.setupmyproject.payment.paypal;

public enum PaypalStatus {
	COMPLETED,INVALID;

	/**
	 * 
	 * @param statusName nome do status que deve ser carregado. Não precisa se preocupar com upper ou lower case.
	 * @return status achado pelo nome ou <b>INVALID</b>
	 */
	public static PaypalStatus load(String statusName) {
		try{ 
			return PaypalStatus.valueOf(statusName.toUpperCase());
		} catch(Exception exception){
			return PaypalStatus.INVALID;
		}
	}

}
