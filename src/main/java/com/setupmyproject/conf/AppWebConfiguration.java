package com.setupmyproject.conf;

import java.util.Arrays;
import java.util.Properties;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.setupmyproject.components.SetupStateDataValueProcessor;

@Configuration
public class AppWebConfiguration {
	
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setExposeContextBeansAsAttributes(true);
		return resolver;
	}

	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(
				true);

		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		registrar.setFormatter(new DateFormatter("yyyy-MM-dd"));
		registrar.registerFormatters(conversionService);
		return conversionService;
	}
	
	@Bean
	public SetupStateDataValueProcessor requestDataValueProcessor(){
		return new SetupStateDataValueProcessor();
	}
	
	@Bean
	@Profile({"production"})
	public MailSender mailSender(AccessEnvironment accessEnvironment) {
	
		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		javaMailSenderImpl.setHost("smtp.gmail.com");
		javaMailSenderImpl.setPassword(accessEnvironment.getProperty("email.password"));
		javaMailSenderImpl.setPort(587);
		javaMailSenderImpl.setUsername(accessEnvironment.getProperty("email.login"));
		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", true);
		mailProperties.put("mail.smtp.starttls.enable", true);
		javaMailSenderImpl.setJavaMailProperties(mailProperties);
		
		return javaMailSenderImpl;
	}
	
	@Bean
	@Profile({"dev","homolog"})
	public MailSender mailSenderDev() {
	
		return new MailSender() {
			
			@Override
			public void send(SimpleMailMessage[] simpleMessages) throws MailException {
				System.out.println(Arrays.toString(simpleMessages));
			}
			
			@Override
			public void send(SimpleMailMessage simpleMessage) throws MailException {
				System.out.println(simpleMessage);
			}
		};
	}	
	
	@Bean
	public ThreadPoolTaskExecutor threadPoolExecutor(){
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(5);
		threadPoolTaskExecutor.setMaxPoolSize(10);
		threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		return threadPoolTaskExecutor;
	}
		
}
