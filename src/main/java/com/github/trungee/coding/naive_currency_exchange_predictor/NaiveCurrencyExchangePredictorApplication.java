package com.github.trungee.coding.naive_currency_exchange_predictor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.github.trungee.coding.naive_currency_exchange_predictor.config.AppConfig;
import com.github.trungee.coding.naive_currency_exchange_predictor.core.Predictor;
import com.github.trungee.coding.naive_currency_exchange_predictor.core.Sample;
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
        String exchangeFrom = args[0].substring(args[0].length() - 3, args[0].length());
        String exchangeTo = args[1].substring(args[1].length() - 3, args[1].length());
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        List<Sample> samples = sampleCollector.collect(exchangeFrom, exchangeTo);
        Predictor predictor = new Predictor(samples);
        predictor.predict();
    }

}
