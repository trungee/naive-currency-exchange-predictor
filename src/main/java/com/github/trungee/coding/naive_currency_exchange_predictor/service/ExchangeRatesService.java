package com.github.trungee.coding.naive_currency_exchange_predictor.service;

import java.time.LocalDate;

import com.github.trungee.coding.naive_currency_exchange_predictor.core.Sample;

public interface ExchangeRatesService {

    Sample getHistoricalExchangesRate(String from, String to, LocalDate date) throws Exception;

}
