package com.setupmyproject.payment.paypal;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.ipn.IPNMessage;
import com.setupmyproject.daos.PaymentDAO;
import com.setupmyproject.daos.RequestedSetupDAO;
import com.setupmyproject.models.CommandGenerators;
import com.setupmyproject.models.RequestedSetup;
import com.setupmyproject.payment.BuyerInfo;
import com.setupmyproject.payment.GenericPaymentStatus;
import com.setupmyproject.payment.Payment;
import com.setupmyproject.payment.PaymentAlreadyDoneException;
import com.setupmyproject.payment.PaymentNotCompletedException;
import com.setupmyproject.payment.PaymentNotConfirmedByTheProviderException;

@Service
public class CallbackPaypalProcessor {

	@Autowired
	private PaymentDAO paymentDAO;
	@Autowired
	private RequestedSetupDAO requestedSetupDAO;
	
	/**
	 * 
	 * @param ipnMessage mensagem associada ao callback da compra do paypal
	 * @param generatedToken token gerado na solicitação do setup
	 * @return 
	 * @throws {@link PaymentAlreadyDoneException}
	 * @throws {@link PaymentNotCompletedException}
	 * @throws {@link PaymentCallbackDataInvalidException}
	 * @throws {@link IllegalArgumentException} caso o token passado não exista
	 */
	public CommandGenerators process(IPNMessage ipnMessage,String generatedToken) {
		/*
		 * Exemplo de dados
		 * Completed
		   121236767
		   seller@paypalsandbox.com
           12.34
           USD
		 */
		
		Optional<RequestedSetup> maybeRequestedSetup = requestedSetupDAO.tryToFindByToken(generatedToken);
		if(!maybeRequestedSetup.isPresent()){
			throw new IllegalArgumentException("There is no setup for this token");
		}
		
		RequestedSetup requestedSetup = maybeRequestedSetup.get();
		
		if(ipnMessage.validate()){		
			String transactionId = ipnMessage.getIpnValue("txn_id");
			
			Optional<Payment> maybePayment = paymentDAO.searchByCode(transactionId);
			if(maybePayment.isPresent()){
				throw new PaymentAlreadyDoneException("The payment was already done");
			}
			
			PaypalStatus currentStatus = PaypalStatus.load(ipnMessage.getIpnValue("payment_status"));
			BuyerInfo buyerInfo = new BuyerInfo(ipnMessage.getIpnValue("first_name"), ipnMessage.getIpnValue("payer_email"));
			
			if(!currentStatus.equals(PaypalStatus.COMPLETED)){
				Payment failedPayment = new Payment(GenericPaymentStatus.INVALID, requestedSetup, transactionId,buyerInfo);
				failedPayment.setCustomDescription("O pagamento não foi liberado pelo paypal. O status retornado foi ("+ipnMessage.getIpnValue("payment_status")+")");
				paymentDAO.save(failedPayment);
				throw new PaymentNotCompletedException("The payment was not well completed");
			}
			
			Payment payment = new Payment(GenericPaymentStatus.COMPLETED,requestedSetup,transactionId,buyerInfo);
			paymentDAO.save(payment);
			
			return requestedSetup.getCommandGenerators();			
		}
		
		throw new PaymentNotConfirmedByTheProviderException("I am not sure about you...");
		
	}
	
	
}
