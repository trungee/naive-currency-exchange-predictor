package com.github.trungee.coding.naive_currency_exchange_predictor;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.github.trungee.coding.naive_currency_exchange_predictor.config.AppConfig;
import com.github.trungee.coding.naive_currency_exchange_predictor.service.NaiveSampleCollector;

@SpringBootApplication
public class NaiveCurrencyExchangePredictorApplication implements CommandLineRunner {
    
	@Autowired
	NaiveSampleCollector sampleCollector;
	
	public static void main(String[] args) {
		SpringApplication.run(NaiveCurrencyExchangePredictorApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
    	String exchangeFrom = "USD";
    	String exchangeTo = "TRY";
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    	NaiveSampleCollector sampleCollector = context.getBean(NaiveSampleCollector.class);
    	List<BigDecimal> exchangeRates = sampleCollector.collect(exchangeFrom, exchangeTo);
    	for (BigDecimal number : exchangeRates) {
    		System.out.println(number);
    	}
    }
	
}
