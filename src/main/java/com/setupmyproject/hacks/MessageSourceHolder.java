package com.setupmyproject.hacks;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.setupmyproject.components.PageMessages;

public class MessageSourceHolder {

	public static PageMessages getPageMessages(){
		HttpServletRequest request = (HttpServletRequest) ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		WebApplicationContext webAppContext = RequestContextUtils.getWebApplicationContext(request);
		MessageSource messageSource = (MessageSource) webAppContext.getBean("messageSource");
		return new PageMessages(messageSource);
	}
}
