package com.setupmyproject.downloads;

import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.setupmyproject.daos.PaymentDAO;
import com.setupmyproject.daos.RequestedSetupDAO;
import com.setupmyproject.exceptions.http.UnauthorizedStatusException;
import com.setupmyproject.models.RequestedSetup;
import com.setupmyproject.payment.Payment;

@Profile({"production","homolog"})
@Component
public class ProductionRequestedSetupUnlocker implements RequestedSetupUnlocker{
	
	@Autowired
	private PaymentDAO paymentDAO;
	@Autowired
	private RequestedSetupDAO requestedSetupDAO;
	private Logger logger = LoggerFactory.getLogger(ProductionRequestedSetupUnlocker.class);

	public void unlock(String token,Function<RequestedSetup, Void> code){
		Optional<Payment> maybePayment = paymentDAO.searchByToken(token);
		if(!maybePayment.isPresent()) {
			logger.info("Downlaod sendo solicitado sem que o pagamento tenha sido confirmado {}",token);
			RequestedSetup requestedSetup = requestedSetupDAO.searchByToken(token);
			executeFunctionCodeAndIncrementDownload(code,requestedSetup);
			return ;
			
		}		
		
		Payment payment = maybePayment.get();
		if(!payment.isOk()){
			throw new UnauthorizedStatusException("You can't download yet. Your payment was not authorized",token);
		}
				
		RequestedSetup requestedSetup = payment.getRequestedSetup();
		executeFunctionCodeAndIncrementDownload(code, requestedSetup);
		
		
	}

	private void executeFunctionCodeAndIncrementDownload(Function<RequestedSetup, Void> code,
			RequestedSetup requestedSetup) {
		code.apply(requestedSetup);
		
		requestedSetup.incrementDownload((setup) -> {
			requestedSetupDAO.updateDownloads(setup.getId(),setup.getDownloadCounter());
			return null;
		});
	}
}
