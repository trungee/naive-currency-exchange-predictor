package com.github.trungee.coding.naive_currency_exchange_predictor.service;

import java.math.BigDecimal;
import java.time.LocalDate;


public interface ExchangeRatesService {

	BigDecimal getHistoricalExchangesRate(String from, String to, LocalDate date) throws Exception;

}
