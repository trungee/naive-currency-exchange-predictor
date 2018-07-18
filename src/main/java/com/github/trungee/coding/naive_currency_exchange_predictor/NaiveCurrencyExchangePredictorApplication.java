package com.github.trungee.coding.naive_currency_exchange_predictor;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.trungee.coding.naive_currency_exchange_predictor.core.Predictor;
import com.github.trungee.coding.naive_currency_exchange_predictor.core.Sample;
import com.github.trungee.coding.naive_currency_exchange_predictor.service.SampleCollector;
import com.github.trungee.coding.naive_currency_exchange_predictor.validator.ArgumentValidator;

@SpringBootApplication
public class NaiveCurrencyExchangePredictorApplication implements CommandLineRunner {

    @Autowired
    SampleCollector sampleCollector;
    
    @Autowired
    ArgumentValidator argumentValidatior;
    
    private static final String OUTPUT_PATTERN = "The predicted currency exchange from %s to %s for 15/%d/2017 is %f.";
    private static final int PREDICT_MONTH_JANUARY = 1;
    
    public static void main(String[] args) {
        SpringApplication.run(NaiveCurrencyExchangePredictorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (argumentValidatior.validate(args)) {
            String exchangeFrom = args[0].substring(args[0].length() - 3, args[0].length());
            String exchangeTo = args[1].substring(args[1].length() - 3, args[1].length());
            List<Sample> samples = sampleCollector.collect(exchangeFrom, exchangeTo);
            Predictor predictor = new Predictor(samples);
            try {
                BigDecimal predictedExchangeRate = predictor.predict(PREDICT_MONTH_JANUARY);
                System.out.println(String.format(OUTPUT_PATTERN, exchangeFrom, exchangeTo, PREDICT_MONTH_JANUARY, predictedExchangeRate.doubleValue()));
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

}
