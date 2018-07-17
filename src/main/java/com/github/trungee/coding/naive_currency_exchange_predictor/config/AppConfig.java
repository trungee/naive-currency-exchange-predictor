package com.github.trungee.coding.naive_currency_exchange_predictor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.github.trungee.coding.naive_currency_exchange_predictor.Predictor;

@Configuration
@ComponentScan("com.github.trungee.coding.naive_currency_exchange_predictor")
@PropertySource(value = { "application.properties" })
public class AppConfig {
	
	@Bean
	public Predictor getPredictor() {
		return new Predictor(null);
	}
	
}
