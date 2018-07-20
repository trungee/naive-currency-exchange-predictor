package com.github.trungee.coding.naive_currency_exchange_predictor.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.github.trungee.coding.naive_currency_exchange_predictor.core.Sample;

@Service
public class MockExchangeRatesService implements ExchangeRatesService{

    @Override
    public Sample getHistoricalExchangesRate(String from, String to,
            LocalDate date) throws Exception {
        BigDecimal exchangeRate;
        if (date != null) {
            switch (date.getMonthValue()) {
            case 1:
            case 3:
            case 5:
                exchangeRate = new BigDecimal("1.2345");
                break;
            default:
                exchangeRate = new BigDecimal("2.3456");
            }
        } else {
            throw new IllegalArgumentException("input date must not be null.");
        }
        
        return new Sample(date, exchangeRate);
    }

}
