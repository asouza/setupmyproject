package com.setupmyproject.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.CookieGenerator;

import com.paypal.ipn.IPNMessage;
import com.setupmyproject.components.BuyerInfoTaken;
import com.setupmyproject.conf.AccessEnvironment;
import com.setupmyproject.controllers.forms.BuyerForm;
import com.setupmyproject.daos.PaymentDAO;
import com.setupmyproject.daos.RequestedSetupDAO;
import com.setupmyproject.models.RequestedSetup;
import com.setupmyproject.payment.Payment;
import com.setupmyproject.payment.PaymentNotCompletedException;
import com.setupmyproject.payment.paypal.CallbackPaypalProcessor;
import com.setupmyproject.wizards.WizardPayment;

@Controller
@RequestMapping("/payment")
@Transactional(noRollbackFor = PaymentNotCompletedException.class)
public class PaymentController {

	@Autowired
	private CallbackPaypalProcessor callbackPaypalProcessor;
	@Autowired
	private AccessEnvironment env;
	@Autowired
	private RequestedSetupDAO requestedSetupDAO;
	@Autowired
	private PaymentDAO paymentDAO;
	@Autowired
	private BuyerInfoTaken buyerInfoTaken;
	
	private Logger logger = LoggerFactory.getLogger(PaymentController.class);


	@RequestMapping("/confirm")
	public ResponseEntity<String> confirm(String token, HttpServletRequest request) {
		Map<String, String> configMap = new HashMap<String, String>();
		configMap.put("mode", env.getProperty("paypal.env"));

		IPNMessage ipnlistener = new IPNMessage(request, configMap);

		callbackPaypalProcessor.process(ipnlistener, token);
		HttpHeaders responseHeaders = new HttpHeaders();
		logger.info("Mais uma compra \\o/!. Token do setup = {}",token);
		return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/form",method=RequestMethod.GET)
	public ModelAndView form(String token){
		RequestedSetup requestedSetup = requestedSetupDAO.searchByToken(token);
		return WizardPayment.formPaymentModelAndView(requestedSetup.getCommandGenerators(), token);
		
	}
	
	@RequestMapping(value="/freemium",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView freemium(String token,BuyerForm userForm,HttpServletResponse response){
		RequestedSetup requestedSetup = requestedSetupDAO.searchByToken(token);
		paymentDAO.save(Payment.freemium(requestedSetup,userForm.buildBuyer()));
		
		if(userForm.isValid()){
			buyerInfoTaken.saveCookie(response);
		}
		return new ModelAndView("redirect:/downloads?token="+token);
	}
}
