package com.setupmyproject.infra;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.guava.GuavaCacheManager;

import com.google.common.cache.CacheBuilder;
import com.setupmyproject.models.SpringAddon;

public class TooltipLoader {
	
	private static Logger logger = LoggerFactory.getLogger(TooltipLoader.class);
	//TODO isolar numa fabrica? estou usando assim pq esse objeto não é gerenciado pelo Spring
	private static CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder()
			.maximumSize(100).expireAfterAccess(5, TimeUnit.MINUTES);
	
	private static GuavaCacheManager cacheManager = new GuavaCacheManager();
	
	static {
		cacheManager.setCacheBuilder(builder);
	}
	

	/**
	 * 
	 * @param tooltipFileName caminho para o arquivo da tooltip, já considerando que está dentro da pasta <b>tooltips</b>. Ex: spring-security-basic.html.
	 * @return
	 */
	public static String read(String tooltipFileName) {
		Cache cache = cacheManager.getCache("tooltips");
		ValueWrapper foundTooltip = cache.get(tooltipFileName);
		if(foundTooltip != null){
			return foundTooltip.get().toString();
		}
		
		InputStream is = SpringAddon.class.getResourceAsStream("/tooltips/"+tooltipFileName);
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(is, writer);
		} catch (Exception e) {
			logger.error("Não foi possível carregar a tooltip para {}. O motivo foi {}",tooltipFileName,e);
			return "No tooltip available";
		}		
		String tooltipText = writer.toString();
		cache.putIfAbsent(tooltipFileName, tooltipText);
		return tooltipText;
	}

	
}
