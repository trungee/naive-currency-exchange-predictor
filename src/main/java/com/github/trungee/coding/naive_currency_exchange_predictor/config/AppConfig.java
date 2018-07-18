package com.github.trungee.coding.naive_currency_exchange_predictor.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.github.trungee.coding.naive_currency_exchange_predictor.core.Predictor;
import com.github.trungee.coding.naive_currency_exchange_predictor.core.Sample;
import com.github.trungee.coding.naive_currency_exchange_predictor.service.NaiveSampleCollector;

@Configuration
@ComponentScan("com.github.trungee.coding.naive_currency_exchange_predictor")
@PropertySource(value = { "application.properties" })
public class AppConfig {


}
