package com.setupmyproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses={SetupMyProjectBoot.class,Jsr310JpaConverters.class})
public class SetupMyProjectBoot extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(SetupMyProjectBoot.class, args);
	}
}
	