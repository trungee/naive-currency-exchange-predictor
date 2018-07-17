package com.github.trungee.coding.naive_currency_exchange_predictor.task;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.Callable;

import com.github.trungee.coding.naive_currency_exchange_predictor.service.ExchangeRatesService;

public class GetExcangeRateTask implements Callable<BigDecimal> {

	private ExchangeRatesService exchangeRatesService;
	private String from;
	private String to;
	private LocalDate date;

	public GetExcangeRateTask(ExchangeRatesService exchangeRatesService, String from, String to, LocalDate date) {
		this.exchangeRatesService = exchangeRatesService;
		this.from = from;
		this.to = to;
		this.date = date;
	}
	
	@Override
	public BigDecimal call() throws Exception {
		return exchangeRatesService.getHistoricalExchangesRate(from, to, date);
	}

}
