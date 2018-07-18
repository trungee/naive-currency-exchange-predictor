package com.github.trungee.coding.naive_currency_exchange_predictor.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.github.trungee.coding.naive_currency_exchange_predictor")
@PropertySource(value = { "application.properties" })
public class AppConfig {
}
