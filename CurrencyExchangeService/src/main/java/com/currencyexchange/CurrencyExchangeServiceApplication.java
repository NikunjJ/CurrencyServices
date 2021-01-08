package com.currencyexchange;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableTransactionManagement
@SpringBootApplication
@PropertySource(value = "file:${ccyexchange.config}", ignoreResourceNotFound = true)
public class CurrencyExchangeServiceApplication {

	public static void main(String[] args) 
	{
		SpringApplication.run(CurrencyExchangeServiceApplication.class, args);
	}

	@Bean
	public LocaleResolver localeResolver() {
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}

}
