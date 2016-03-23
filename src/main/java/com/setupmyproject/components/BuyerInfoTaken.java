package com.setupmyproject.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.util.CookieGenerator;
import org.springframework.web.util.WebUtils;

/**
 * Grava o cookie para dizer que já pegamos info do usuário
 * @author alberto
 *
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BuyerInfoTaken {
	
	private static final String BUYER_COOKIE = "setupmyproject_downloader";
	@Autowired
	private NativeWebRequest request;	

	public void saveCookie(HttpServletResponse response) {
		CookieGenerator cookieGenerator = new CookieGenerator();
		cookieGenerator.setCookieName(BUYER_COOKIE);
		int tenYears = 315360000;
		cookieGenerator.setCookieMaxAge(tenYears);
		cookieGenerator.addCookie(response, "ok");
	}
	
	/**
	 * verifica se o cookie foi salvo
	 * @return
	 */
	public boolean isDone(){
		return WebUtils.getCookie(request.getNativeRequest(HttpServletRequest.class), BUYER_COOKIE)!=null;
	}
	
	

}
