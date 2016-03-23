package com.setupmyproject.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Component
public class PageMessages {

	@Autowired
	private MessageSource messageSource;

	/**
	 * construtor para ser usado pelo spring
	 */
	public PageMessages() {
	}
	
	public PageMessages(MessageSource messageSource) {
		this.messageSource = messageSource;
	}


	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	
	public String getMessage(String key) {
		return messageSource.getMessage(key, new Object[]{},LocaleContextHolder.getLocale());
		
	}
	
	public String getMessage(String key,String defaultMessage) {
		return messageSource.getMessage(key, new Object[]{}, defaultMessage,LocaleContextHolder.getLocale());

	}
	
	public void info(String key,String defaultMessage,RedirectAttributes redirectAttributes){
		redirectAttributes.addFlashAttribute("info", getMessage(key,defaultMessage));
	}
	
	public void success(String key,String defaultMessage,RedirectAttributes redirectAttributes){
		redirectAttributes.addFlashAttribute("success", getMessage(key,defaultMessage));
	}
	
	public void error(String key,String defaultMessage,RedirectAttributes redirectAttributes){
		redirectAttributes.addFlashAttribute("error", getMessage(key,defaultMessage));
	}
	
	public void info(String key,String defaultMessage,ModelAndView redirectAttributes){
		redirectAttributes.addObject("info", getMessage(key,defaultMessage));
	}
	
	public void success(String key,String defaultMessage,ModelAndView redirectAttributes){
		redirectAttributes.addObject("success", getMessage(key,defaultMessage));
	}
	
	public void error(String key,String defaultMessage,ModelAndView redirectAttributes){
		redirectAttributes.addObject("error", getMessage(key,defaultMessage));
	}
}
