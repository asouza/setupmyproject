package com.setupmyproject.payment;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.setupmyproject.models.RequestedSetup;

@Entity
public class Payment {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Enumerated(EnumType.STRING)
	private GenericPaymentStatus status;
	@ManyToOne
	private RequestedSetup requestedSetup;
	private String transactionId;
	private BigDecimal duplicatedPrice;
	private String duplicatedToken;
	private String customDescription;
	private Calendar creationDate = Calendar.getInstance();
	@Embedded
	private BuyerInfo buyerInfo;

	@Deprecated
	public Payment() {
	}

	public Payment(GenericPaymentStatus status, RequestedSetup requestedSetup,
			String transactionId,BuyerInfo buyerInfo) {
		this.status = status;
		this.requestedSetup = requestedSetup;
		this.transactionId = transactionId;
		this.duplicatedPrice = requestedSetup.getCommandGenerators().getFinalPrice();
		this.duplicatedToken = requestedSetup.getGeneratedToken();
		this.buyerInfo = buyerInfo;
	}
	
	public void setCustomDescription(String customDescription) {
		this.customDescription = customDescription;
	}

	public boolean isOk() {
		return status.equals(GenericPaymentStatus.COMPLETED) || status.equals(GenericPaymentStatus.FREEMIUM);
	}

	public RequestedSetup getRequestedSetup() {
		return requestedSetup;
	}

	public static Payment freemium(RequestedSetup requestedSetup,BuyerInfo buyerInfo) {
		String freemiumTransactionId = "0000000";
		Payment payment = new Payment(GenericPaymentStatus.FREEMIUM, requestedSetup, freemiumTransactionId,buyerInfo);
		payment.setCustomDescription("Pagamento liberado para atrair o usuário de novo");
		return payment;
	}

}
